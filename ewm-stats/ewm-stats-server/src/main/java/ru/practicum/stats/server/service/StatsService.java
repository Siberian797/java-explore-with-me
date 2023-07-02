package ru.practicum.stats.server.service;

import ru.practicum.stats.dto.EndpointHitRequestDto;
import ru.practicum.stats.dto.EndpointHitResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void createHit(EndpointHitRequestDto endpointHitRequestDto);
    List<EndpointHitResponseDto> getHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
