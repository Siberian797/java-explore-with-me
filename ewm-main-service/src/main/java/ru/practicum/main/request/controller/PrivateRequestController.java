package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping("/requests")
    List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.info("GET-requests (private) has been called");
        return requestService.getAllRequests(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/requests")
    ParticipationRequestDto createUserRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("POST-requests (private) has been called");
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    ParticipationRequestDto cancelUserRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("PATCH-requests (private) has been called");
        return requestService.cancelUserRequest(userId, requestId);
    }

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET-event-requests (private) has been called");
        return requestService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateUserEventRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                                                 @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("PATCH-event-requests (private) has been called");
        return requestService.updateRequest(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
