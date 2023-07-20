package ru.practicum.main.exception.model;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String entity, Long id) {
        super(String.format("Entity %s with id %d called conflict", entity, id));
    }
}
