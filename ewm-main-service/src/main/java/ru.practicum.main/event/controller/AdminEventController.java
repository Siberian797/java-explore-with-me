package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                               @RequestParam(required = false) List<CommonConstants.EventState> states,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT) LocalDateTime rangeStart,
                                               @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now().plusYears(50)}") @DateTimeFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT) LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getAllEventsForAdmin(
            users, states, categories, rangeStart, rangeEnd, PageRequest.of(from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                           @RequestBody UpdateEventRequest<CommonConstants.EventStateAdminAction> updateEventRequest) {
        return eventService.updateAdminEvent(eventId, updateEventRequest);
    }
}
