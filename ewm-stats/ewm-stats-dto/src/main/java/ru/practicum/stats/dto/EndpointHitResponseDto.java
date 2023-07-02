package ru.practicum.stats.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class EndpointHitResponseDto implements Serializable {
    String app;
    String uri;
    Long hits;
}
