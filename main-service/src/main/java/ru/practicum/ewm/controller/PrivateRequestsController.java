package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestsController {
    // TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getRequests(
            @PathVariable long userId
    ) {
        // вызов сервиса
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addRequest(
            @PathVariable long userId,
            @RequestParam long eventId
    ) {
        // вызов сервиса
    }

    @PatchMapping("/{requestsId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelRequest(
            @PathVariable long userId,
            @PathVariable long requestId
    ) {
        // вызов сервиса
    }
}
