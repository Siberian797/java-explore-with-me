package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.utils.CommonConstants;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewEventDto {
    @NotBlank
    @Size(min = 3, max = 120)
    String title;

    @NotBlank
    @Size(min = 20, max = 7000)
    String description;

    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;

    @NotNull
    @Positive
    Long category;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime eventDate;

    @NotNull
    LocationDto location;

    @Builder.Default
    Boolean paid = false;

    @Builder.Default
    @PositiveOrZero
    Integer participantLimit = 0;

    @Builder.Default
    Boolean requestModeration = true;
}
