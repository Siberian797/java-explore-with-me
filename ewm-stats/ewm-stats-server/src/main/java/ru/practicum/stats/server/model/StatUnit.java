package ru.practicum.stats.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatUnit {
    private String app;
    private String uri;
    private Long hits;
}
