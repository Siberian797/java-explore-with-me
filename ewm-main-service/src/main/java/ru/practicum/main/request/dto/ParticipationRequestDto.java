package ru.practicum.main.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.request.constant.RequestStatus;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class ParticipationRequestDto {
    Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    Long event;

    Long requester;

    RequestStatus status;
}
