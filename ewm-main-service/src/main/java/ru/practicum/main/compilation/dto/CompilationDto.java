package ru.practicum.main.compilation.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.List;

@Value
@Builder
public class CompilationDto {
    Long id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}
