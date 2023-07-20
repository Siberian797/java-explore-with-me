package ru.practicum.main.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class NewCategoryDto {
    @NotBlank
    @Size(min = 1, max = 50)
    String name;
}
