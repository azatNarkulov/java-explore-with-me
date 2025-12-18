package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    UserDto toDto(User user);

    @Mapping(target = "id", source = "id")
    UserShortDto toShortDto(User user);

    User toEntity(UserDto dto);

    User toEntity(NewUserRequest dto);
}
