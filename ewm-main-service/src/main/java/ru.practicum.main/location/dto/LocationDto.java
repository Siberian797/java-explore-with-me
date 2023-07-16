package ru.practicum.main.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
@AllArgsConstructor
public class LocationDto implements Serializable {
    Float lat;
    Float lon;
}
