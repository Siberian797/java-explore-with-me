package ru.practicum.main.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.utils.CommonConstants;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getAllEventsForUser(Long userId, PageRequest pageRequest);

    List<EventFullDto> getAllEventsForAdmin(List<Long> users, List<CommonConstants.EventState> states, List<Long> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    EventFullDto readEvent(Long userId, Long eventId);

    EventFullDto readPublicEvent(Long id, HttpServletRequest request);

    EventFullDto updateUserEvent(Long userId, Long eventId, UpdateEventRequest<CommonConstants.EventStateUserAction> updateEventRequest);

    EventFullDto updateAdminEvent(Long eventId, UpdateEventRequest<CommonConstants.EventStateAdminAction> updateEventRequest);

    List<EventShortDto> getAllPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Boolean onlyAvailable, CommonConstants.EventSort sort,
                                           Integer from, Integer size, HttpServletRequest request);
}
