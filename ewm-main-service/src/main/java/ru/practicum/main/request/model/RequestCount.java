package ru.practicum.main.request.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestCount {
    private final Long eventId;
    private final Integer count;
}
