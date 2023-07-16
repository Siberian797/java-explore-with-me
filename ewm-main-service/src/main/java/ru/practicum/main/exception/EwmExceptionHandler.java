package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.exception.dto.ExceptionResponse;
import ru.practicum.main.exception.model.EntityConflictException;
import ru.practicum.main.exception.model.EntityNotFoundException;
import ru.practicum.main.exception.model.EntityNotValidException;

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

    @ExceptionHandler(EntityConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleEntityConflictException(EntityConflictException e) {
        return ExceptionResponse.builder()
                .message(e.getMessage())
                .reason("Conflict")
                .status(HttpStatus.NOT_FOUND.toString())
                .build();
    }

    @ExceptionHandler(EntityNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequest(EntityNotValidException e) {
        return ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Validation")
                .build();
    }
}
