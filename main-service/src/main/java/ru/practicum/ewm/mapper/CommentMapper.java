package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.model.Comment;

@Mapper(
        componentModel = "spring",
        uses = {EventMapper.class, UserMapper.class}
)
public interface CommentMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "event", source = "event")
    CommentDto toDto(Comment comment);

    Comment toEntity(NewCommentDto dto);
}
