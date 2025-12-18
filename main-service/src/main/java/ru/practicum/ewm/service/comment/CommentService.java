package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto create(Long eventId, Long userId, NewCommentDto dto);

    void delete(Long commentId);

    List<CommentDto> getAll(Long eventId);
}
