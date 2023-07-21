package ru.practicum.main.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
@AllArgsConstructor
public class CommentShortDto {
    @NotBlank
    Long id;

    @NotBlank
    @Size(max = 2000)
    String text;
}
