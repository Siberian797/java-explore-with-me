package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.utils.CommonConstants;
import ru.practicum.main.user.dto.UserShortDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@Builder
public class EventShortDto implements Serializable {
    Long id;
    String annotation;
    transient CategoryDto category;
    Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime eventDate;
    transient UserShortDto initiator;
    Boolean paid;
    String title;
    Integer views;
}
