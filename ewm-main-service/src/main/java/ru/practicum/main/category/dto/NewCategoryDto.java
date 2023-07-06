package ru.practicum.main.category.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Value
public class NewCategoryDto implements Serializable {
    @NotBlank
    @Size(min = 1, max = 50)
    String name;
}
