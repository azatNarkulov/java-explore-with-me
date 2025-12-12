package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDto create(NewUserRequest dto);

    void delete(Long id);

    List<UserDto> getUsers(Set<Long> ids, int from, int size);
}
