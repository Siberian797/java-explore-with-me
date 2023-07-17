package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.stats.dto.utils.CommonConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class EndpointHitRequestDto {
    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @NotBlank
    private String ip;
    @NotNull
    @DateTimeFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    @JsonFormat(pattern = CommonConstants.COMMON_JSON_DATETIME_FORMAT)
    private LocalDateTime timestamp;
}
