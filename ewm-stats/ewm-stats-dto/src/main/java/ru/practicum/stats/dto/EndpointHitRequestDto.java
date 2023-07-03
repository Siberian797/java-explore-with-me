package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.stats.dto.utils.CommonConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class EndpointHitRequestDto implements Serializable {
    @NotBlank
    String app;
    @NotBlank
    String uri;
    @NotBlank
    String ip;
    @NotNull
    @DateTimeFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    @JsonFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    LocalDateTime timestamp;
}
