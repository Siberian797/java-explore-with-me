package ru.practicum.stats.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EndpointHitResponseDto {
    String app;
    String uri;
    Long hits;
}
