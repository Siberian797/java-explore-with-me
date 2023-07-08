package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.exception.dto.ExceptionResponse;
import ru.practicum.main.exception.model.EntityNotFoundException;

@RestControllerAdvice("ru.practicum.main")
public class EwmExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return ExceptionResponse.builder()
                .message(e.getMessage())
                .reason("Not found")
                .status(HttpStatus.NOT_FOUND.toString())
                .build();
    }
}
