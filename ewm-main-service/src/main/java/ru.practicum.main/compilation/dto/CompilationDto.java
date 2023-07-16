package ru.practicum.main.compilation.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.event.dto.EventShortDto;

import java.io.Serializable;
import java.util.List;

@Value
@Builder
public class CompilationDto implements Serializable {
    Long id;
    transient List<EventShortDto> events;
    Boolean pinned;
    String title;
}
