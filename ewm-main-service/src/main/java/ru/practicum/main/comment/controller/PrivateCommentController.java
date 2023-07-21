package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.service.CommentService;

@Validated
@RestController
@RequestMapping("/events/{userId}/comments")
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentFullDto createComment(@PathVariable Long userId,
                                        @RequestBody @Validated CommentFullDto commentFullDto) {
        log.info("POST-comments (private) has been called");
        return commentService.createComment(commentFullDto, userId);
    }

    @PatchMapping
    public CommentFullDto updateComment(@PathVariable Long userId,
                                        @RequestBody @Validated CommentShortDto commentShortDto) {
        log.info("PATCH-comment (private) has been called");
        return commentService.updateComment(userId, commentShortDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        log.info("DELETE-comment (private) has been called");
        commentService.deleteComment(userId, commentId);
    }
}
