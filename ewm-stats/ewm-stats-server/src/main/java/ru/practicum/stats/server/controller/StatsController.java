package ru.practicum.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitRequestDto;
import ru.practicum.stats.dto.EndpointHitResponseDto;
import ru.practicum.stats.server.service.StatsService;
import ru.practicum.stats.server.utils.CommonConstants;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEndpointHit(@Validated @RequestBody EndpointHitRequestDto endpointHitRequestDto) {
        log.info("POST-hit has been called");
        service.createHit(endpointHitRequestDto);
    }

    @GetMapping("/stats")
    public List<EndpointHitResponseDto> getEndpointHits(
            @RequestParam("start") @DateTimeFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT) LocalDateTime end,
            @RequestParam(name = "uris", defaultValue = "") List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("GET-stats has been called");
        return service.getHits(start, end, uris, unique);
    }
}