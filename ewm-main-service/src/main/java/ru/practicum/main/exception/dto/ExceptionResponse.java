package ru.practicum.main.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ExceptionResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    private String message;

    private String reason;

    private String status;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    private LocalDateTime timestamp = LocalDateTime.now();
}
