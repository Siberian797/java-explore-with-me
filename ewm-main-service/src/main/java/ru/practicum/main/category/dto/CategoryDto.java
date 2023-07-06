package ru.practicum.main.category.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class CategoryDto implements Serializable {
    Long id;
    String name;
}
