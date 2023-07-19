package ru.practicum.main.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.model.EntityConflictException;
import ru.practicum.main.exception.model.EntityNotFoundException;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repository.RequestRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("event", eventId));
        User user = validateUser(userId);

        if (Boolean.TRUE.equals(requestRepository.existsByEventIdAndRequesterId(event.getId(), userId))) {
            throw new EntityConflictException("request", event.getId());
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new EntityConflictException("initiator", userId);
        }

        if (event.getState() != CommonConstants.EventState.PUBLISHED) {
            throw new EntityConflictException("event", event.getId());
        }

        if (event.getParticipantLimit() > 0) {
            if (event.getParticipants().size() >= event.getParticipantLimit()) {
                throw new EntityConflictException("participants", event.getId());
            }
        }

        return RequestMapper.toDto(requestRepository.save(Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .status(event.getParticipantLimit() == 0 ? CommonConstants.RequestStatus.CONFIRMED :
                        Boolean.TRUE.equals(event.getRequestModeration()) ? CommonConstants.RequestStatus.PENDING : CommonConstants.RequestStatus.CONFIRMED)
                .build()));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) {
        User user = validateUser(userId);
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new EntityNotFoundException("request", requestId));

        if (!user.getId().equals(request.getRequester().getId())) {
            throw new EntityConflictException("requester", request.getRequester().getId());
        }

        request.setStatus(CommonConstants.RequestStatus.CANCELED);
        return RequestMapper.toDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
        return requestRepository.findByEventInitiatorIdAndEventId(userId, eventId).stream().map(RequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequest(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("event", eventId));

        if (event.getParticipants().size() >= event.getParticipantLimit()) {
            throw new EntityConflictException("event", eventId);
        }

        if (event.getState() != CommonConstants.EventState.PUBLISHED) {
            throw new EntityConflictException("event", eventId);
        }

        List<Request> requests = requestRepository.findAllById(eventRequestStatusUpdateRequest.getRequestIds());

        requests.forEach(request -> {
            if (Boolean.FALSE.equals(event.getRequestModeration()) || event.getParticipantLimit() == 0) {
                return;
            }

            if (eventRequestStatusUpdateRequest.getStatus() == CommonConstants.NewRequestStatus.REJECTED) {
                request.setStatus(CommonConstants.RequestStatus.REJECTED);
                rejectedRequests.add(RequestMapper.toDto(request));
            }

            if (eventRequestStatusUpdateRequest.getStatus() == CommonConstants.NewRequestStatus.CONFIRMED) {
                request.setStatus(CommonConstants.RequestStatus.CONFIRMED);
                confirmedRequests.add(RequestMapper.toDto(request));
            }
        });

        return EventRequestStatusUpdateResult.builder().confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests).build();
    }

    @Override
    public List<ParticipationRequestDto> getAllRequests(Long userId) {
        validateUser(userId);
        List<Request> requests = requestRepository.findByRequesterId(userId);
        return requests.stream().map(RequestMapper::toDto).collect(Collectors.toList());
    }

    private User validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user", userId));
    }
}
