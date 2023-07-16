package ru.practicum.main.exception.model;

public class EntityNotValidException extends RuntimeException {
    public EntityNotValidException(String entity, Long id) {
        super(String.format("Entity %s with id %d was invalid", entity, id));
    }
}
