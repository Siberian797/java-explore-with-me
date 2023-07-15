package ru.practicum.main.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {
    public static final String COMMON_JSON_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public enum EventSort {
        EVENT_DATE, VIEWS
    }

    public enum EventState {
        PENDING, PUBLISHED, CANCELED
    }

    public enum EventStateAdminAction {
        PUBLISH_EVENT, REJECT_EVENT
    }

    public enum EventStateUserAction {
        SEND_TO_REVIEW, CANCEL_REVIEW
    }
}
