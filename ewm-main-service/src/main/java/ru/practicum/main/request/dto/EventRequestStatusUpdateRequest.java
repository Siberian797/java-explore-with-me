package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.utils.CommonConstants;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    CommonConstants.NewRequestStatus status;
}
