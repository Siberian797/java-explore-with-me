package ru.practicum.main.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class LocationDto {
    Float lat;
    Float lon;
}
