package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.model.event.Event;

@Mapper(
        componentModel = "spring",
        uses = {CategoryMapper.class, UserMapper.class, LocationMapper.class}
)
public interface EventMapper {

    @Mapping(target = "views", source = "views")
    @Mapping(target = "confirmedRequests", ignore = true)
    EventFullDto toFullDto(Event event, long views);

    @Mapping(target = "views", source = "views")
    @Mapping(target = "confirmedRequests", ignore = true)
    EventShortDto toShortDto(Event event, long views);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    Event toEntity(NewEventDto dto);
}
