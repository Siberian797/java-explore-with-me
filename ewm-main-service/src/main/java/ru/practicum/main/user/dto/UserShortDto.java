package ru.practicum.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
@AllArgsConstructor
public class UserShortDto implements Serializable {
    Long id;
    String name;
}
