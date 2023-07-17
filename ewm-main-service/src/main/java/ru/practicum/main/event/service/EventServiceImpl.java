package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.model.EntityConflictException;
import ru.practicum.main.exception.model.EntityNotFoundException;
import ru.practicum.main.exception.model.EntityNotValidException;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.location.mapper.LocationMapper;
import ru.practicum.main.location.model.Location;
import ru.practicum.main.location.repository.LocationRepository;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;
import ru.practicum.main.utils.CommonConstants;
import ru.practicum.main.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private static final String EVENTS_PREFIX = "/events/";

    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatsClient statsClient = new StatsClient("http://stats-server:9090", new RestTemplateBuilder());

    @Override
    public EventFullDto readPublicEvent(Long eventId, HttpServletRequest request) {
        CommonUtils.makePublicEndpointHit(statsClient, request);
        return parseToFullDtoWithMappers(
                eventRepository.findByIdAndPublished(eventId).orElseThrow(() -> new EntityNotFoundException("event", eventId)));
    }


    @Override
    public List<EventFullDto> getAllEventsForAdmin(
            List<Long> users, List<CommonConstants.EventState> states, List<Long> categories, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, PageRequest pageRequest) {

        return eventRepository.getEventsForAdmin(users, states, categories, rangeStart, rangeEnd, pageRequest).stream()
                .map(this::parseToFullDtoWithMappers).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateAdminEvent(Long eventId, UpdateEventRequest<CommonConstants.EventStateAdminAction> updateEventRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("event", eventId));

        if (updateEventRequest.getEventDate().isBefore(LocalDateTime.now())) {
            throw new EntityNotValidException("request", null);
        }

        checkEventState(event.getState());
        updateEvent(updateEventRequest, event);

        Optional.ofNullable(updateEventRequest.getStateAction()).ifPresent(state -> {
            switch (state) {
                case PUBLISH_EVENT:
                    if (event.getState() != CommonConstants.EventState.PENDING) {
                        throw new EntityConflictException("status", null);
                    }

                    event.setState(CommonConstants.EventState.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;

                case REJECT_EVENT:
                    event.setState(CommonConstants.EventState.CANCELED);
                    break;
                default:
                    throw new EntityConflictException("state", null);
            }
        });

        return parseToFullDtoWithMappers(event);
    }

    @Override
    public List<EventShortDto> getAllEventsForUser(Long userId, PageRequest pageRequest) {
        CommonUtils.checkAndReturnUser(userRepository, userId);

        List<Event> events = eventRepository.getByInitiatorIdOrderByEventDateDesc(userId, pageRequest);

        return events.stream().map(this::parseToShortDtoWithMappers).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        checkEventDate(newEventDto.getEventDate());

        User user = CommonUtils.checkAndReturnUser(userRepository, userId);
        Location location = findOrCreateLocation(locationRepository, newEventDto.getLocation());
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("category", newEventDto.getCategory()));

        Event event = EventMapper.toEntity(newEventDto, category, location, user);
        return parseToFullDtoWithMappers(eventRepository.save(event));
    }

    @Override
    public EventFullDto readEvent(Long userId, Long eventId) {
        return parseToFullDtoWithMappers(eventRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException("event", eventId)));
    }

    @Override
    @Transactional
    public EventFullDto updateUserEvent(
            Long userId, Long eventId, UpdateEventRequest<CommonConstants.EventStateUserAction> updateEventRequest) {

        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException("event", eventId));

        checkEventInitiator(userId, event.getInitiator());
        checkEventState(event.getState());
        updateEvent(updateEventRequest, event);

        Optional.ofNullable(updateEventRequest.getStateAction()).ifPresent(state -> {
            switch (state) {
                case CANCEL_REVIEW:
                    event.setState(CommonConstants.EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(CommonConstants.EventState.PENDING);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                default:
                    throw new EntityConflictException("state", null);
            }
        });

        return parseToFullDtoWithMappers(event);
    }

    @Override
    public List<EventShortDto> getAllPublicEvents(
            String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Boolean onlyAvailable, CommonConstants.EventSort sort, Integer from, Integer size, HttpServletRequest request) {

        if (rangeEnd.isBefore(rangeStart)) {
            throw new EntityNotValidException("range", null);
        }

        CommonUtils.makePublicEndpointHit(statsClient, request);
        PageRequest pageRequest;
        List<Event> events;

        if (sort == CommonConstants.EventSort.EVENT_DATE) {
            pageRequest = PageRequest.of(from, size, Sort.by("eventDate"));
        } else {
            pageRequest = PageRequest.of(from, size);
        }

        if (Boolean.TRUE.equals(onlyAvailable)) {
            events =
                    eventRepository.getAvailableEventsForUser(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        } else {
            events = eventRepository.getEventsForUser(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        }

        if (sort == CommonConstants.EventSort.VIEWS) {
            sortEventsByViews(events);
        }

        return events.stream().map(this::parseToShortDtoWithMappers).collect(Collectors.toList());
    }

    private void sortEventsByViews(List<Event> events) {
        Map<String, Long> stats = getEventStats(events);

        events.sort((a, b) -> stats.getOrDefault(EVENTS_PREFIX + b.getId(), 0L)
                .compareTo(stats.getOrDefault(EVENTS_PREFIX + a.getId(), 0L)));
    }

    private Map<String, Long> getEventStats(List<Event> events) {
        List<String> uris = events
                .stream()
                .map(event -> EVENTS_PREFIX + event.getId())
                .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.COMMON_JSON_DATETIME_FORMAT);
        String start = LocalDateTime.now().minusYears(10).format(formatter);
        String end = LocalDateTime.now().plusYears(10).format(formatter);

        return CommonUtils.getViews(statsClient, start, end, uris, false);
    }

    private void checkEventState(CommonConstants.EventState state) {
        if (state == CommonConstants.EventState.PUBLISHED) {
            throw new EntityConflictException("status", null);
        }
    }

    private EventShortDto parseToShortDtoWithMappers(Event event) {
        return EventMapper.toShortDto(event, CategoryMapper.toDto(event.getCategory()),
                UserMapper.toShortDto(event.getInitiator())
        );
    }

    private EventFullDto parseToFullDtoWithMappers(Event event) {
        return EventMapper.toDto(eventRepository.save(event), CategoryMapper.toDto(event.getCategory()),
                UserMapper.toShortDto(event.getInitiator()), LocationMapper.toDto(event.getLocation())
        );
    }

    private void checkEventInitiator(Long userId, User initiator) {
        if (!Objects.equals(userId, initiator.getId())) {
            throw new EntityConflictException("initiator", initiator.getId());
        }
    }

    private void checkEventDate(LocalDateTime eventDate) {
        if (LocalDateTime.now().plusHours(2).isAfter(eventDate)) {
            throw new EntityConflictException("eventDate", null);
        }
    }

    private void updateEvent(UpdateEventRequest<?> updateEventRequest, Event event) {
        Optional.ofNullable(updateEventRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updateEventRequest.getCategory())
                .ifPresent(categoryId -> event.setCategory(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("category", categoryId))));
        Optional.ofNullable(updateEventRequest.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updateEventRequest.getEventDate()).ifPresent(eventDate -> {
            checkEventDate(eventDate);
            event.setEventDate(eventDate);
        });
        Optional.ofNullable(updateEventRequest.getLocation())
                .ifPresent(locationDto -> event.setLocation(findOrCreateLocation(locationRepository, locationDto)));
        Optional.ofNullable(updateEventRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateEventRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(updateEventRequest.getRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(updateEventRequest.getTitle()).ifPresent(event::setTitle);
    }

    private Location findOrCreateLocation(LocationRepository locationRepository, LocationDto locationDto) {
        Float lat = locationDto.getLat();
        Float lon = locationDto.getLon();

        return locationRepository.findByLocation(lat, lon)
                .orElse(locationRepository.save(LocationMapper.toEntity(locationDto)));
    }
}