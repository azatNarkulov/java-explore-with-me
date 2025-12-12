package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.model.Event;

@Mapper(
        componentModel = "spring",
        uses = {CategoryMapper.class, UserMapper.class, LocationMapper.class}
)
public interface EventMapper {

    @Mapping(target = "views", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
//    @Mapping(target = "initiator", ignore = true)
//    @Mapping(target = "category", ignore = true)
    EventFullDto toFullDto(Event event);

    @Mapping(target = "views", ignore = true)
    EventShortDto toShortDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    Event toEntity(NewEventDto dto);
}
