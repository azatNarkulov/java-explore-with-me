package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {
    // TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        // вызов сервиса
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable long userId
    ) {
        // вызов сервиса
    }
}
