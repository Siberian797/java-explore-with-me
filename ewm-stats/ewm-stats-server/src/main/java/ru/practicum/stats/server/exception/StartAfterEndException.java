package ru.practicum.stats.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StartAfterEndException extends RuntimeException {
    public StartAfterEndException() {
        super("Start was after and. Check up your input.");
    }
}
