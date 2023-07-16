package ru.practicum.main.category.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
    Long id;
    String name;
}
