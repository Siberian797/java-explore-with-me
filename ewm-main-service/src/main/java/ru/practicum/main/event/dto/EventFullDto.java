package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;

@Value
@Builder
public class EventFullDto {
    Long id;
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime createdOn;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime eventDate;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime publishedOn;
    Boolean requestModeration;
    CommonConstants.EventState state;
    String title;
    Integer views;
}
