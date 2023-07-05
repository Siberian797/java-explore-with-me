package ru.practicum.stats.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class EndpointHitResponseDto implements Serializable {
    String app;
    String uri;
    Long hits;
}
