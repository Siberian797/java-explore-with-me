package ru.practicum.stats.server.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.stats.dto.EndpointHitRequestDto;
import ru.practicum.stats.dto.EndpointHitResponseDto;
import ru.practicum.stats.server.model.EndpointHit;
import ru.practicum.stats.server.model.StatUnit;

@UtilityClass
public class EndpointHitMapper {
    public static EndpointHitResponseDto toDto(StatUnit statUnit) {
        return EndpointHitResponseDto.builder()
                .hits(statUnit.getHits())
                .uri(statUnit.getUri())
                .app(statUnit.getApp())
                .build();
    }

    public static EndpointHit toHit(EndpointHitRequestDto endpointHitRequestDto) {
        return EndpointHit.builder()
                .app(endpointHitRequestDto.getApp())
                .uri(endpointHitRequestDto.getUri())
                .ip(endpointHitRequestDto.getIp())
                .timestamp(endpointHitRequestDto.getTimestamp())
                .build();
    }
}
