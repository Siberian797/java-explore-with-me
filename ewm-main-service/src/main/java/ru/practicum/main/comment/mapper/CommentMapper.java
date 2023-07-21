package ru.practicum.main.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.model.Comment;

@UtilityClass
public class CommentMapper {
    public static CommentFullDto toDto(Comment comment) {
        return CommentFullDto.builder()
                .id(comment.getId())
                .eventId(comment.getEventId())
                .text(comment.getText())
                .commentatorId(comment.getCommentatorId())
                .build();
    }

    public static Comment toEntity(CommentFullDto commentFullDto) {
        return Comment.builder()
                .id(commentFullDto.getId())
                .eventId(commentFullDto.getEventId())
                .text(commentFullDto.getText())
                .commentatorId(commentFullDto.getCommentatorId())
                .build();
    }
}
