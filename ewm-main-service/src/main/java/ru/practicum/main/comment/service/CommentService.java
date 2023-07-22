package ru.practicum.main.comment.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentShortDto;

import java.util.List;

public interface CommentService {
    CommentFullDto createComment(CommentFullDto commentFullDto, Long userId);

    CommentFullDto getComment(Long commentId);

    CommentFullDto updateComment(Long userId, CommentShortDto commentShortDto);

    void deleteComment(Long userId, Long commentId);

    List<CommentFullDto> getAllCommentsByEventId(Long eventId, PageRequest pageRequest);
}
