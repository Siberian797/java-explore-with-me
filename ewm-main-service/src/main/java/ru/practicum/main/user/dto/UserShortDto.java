package ru.practicum.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class UserShortDto {
    Long id;
    String name;
}
