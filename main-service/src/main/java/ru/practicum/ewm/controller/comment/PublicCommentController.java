package ru.practicum.ewm.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.service.comment.CommentService;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAll(@PathVariable Long eventId) {
        return service.getAll(eventId);
    }
}
