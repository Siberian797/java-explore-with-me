package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getAllRequests(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelUserRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequest(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
