package ru.practicum.main.compilation.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class UpdateCompilationRequestDto implements Serializable {
    transient List<Long> events;
    Boolean pinned;
    String title;
}
