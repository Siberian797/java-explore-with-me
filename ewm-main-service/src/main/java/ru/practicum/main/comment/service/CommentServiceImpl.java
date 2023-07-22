package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.model.EntityNotFoundException;
import ru.practicum.main.exception.model.EntityNotValidException;
import ru.practicum.main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentFullDto createComment(CommentFullDto commentFullDto, Long userId) {
        validateUser(userId);
        validateEvent(commentFullDto.getEventId());

        Comment comment = commentRepository.save(CommentMapper.toEntity(commentFullDto));
        comment.setCreatedOn(LocalDateTime.now());

        return CommentMapper.toDto(comment);
    }

    @Override
    public CommentFullDto getComment(Long commentId) {
        return CommentMapper.toDto(getValidatedComment(commentId));
    }

    @Override
    public CommentFullDto updateComment(Long userId, CommentShortDto commentShortDto) {
        validateOwner(commentShortDto.getId(), userId);

        Comment comment = getValidatedComment(commentShortDto.getId());
        comment.setText(commentShortDto.getText());
        comment.setEditedOn(LocalDateTime.now());

        return CommentMapper.toDto(comment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        validateOwner(commentId, userId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentFullDto> getAllCommentsByEventId(Long eventId, PageRequest pageRequest) {
        validateEvent(eventId);

        return commentRepository.findByEventId(eventId, pageRequest)
                .stream().map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user", userId));
    }

    private void validateEvent(Long eventId) {
        eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("event", eventId));
    }

    private void validateOwner(Long commentId, Long userId) {
        validateUser(userId);
        Comment comment = getValidatedComment(commentId);
        if (!Objects.equals(comment.getCommentatorId(), userId)) {
            throw new EntityNotValidException("comment", commentId);
        }
    }

    private Comment getValidatedComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()
                -> new EntityNotFoundException("comment", commentId));
    }
}
