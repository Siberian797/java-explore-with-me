package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentFullDto getComment(@PathVariable Long commentId) {
        log.info("GET-comment (public) has been called");
        return commentService.getComment(commentId);
    }

    @GetMapping
    public List<CommentFullDto> getAllCommentByEventId(@RequestParam Long eventId,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-comments (public) has been called");
        return commentService.getAllCommentsByEventId(eventId, PageRequest.of(from, size));
    }
}
