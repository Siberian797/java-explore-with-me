package ru.practicum.main.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class ParticipationRequestDto {
    Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime created;

    Long event;

    Long requester;

    CommonConstants.RequestStatus status;
}
