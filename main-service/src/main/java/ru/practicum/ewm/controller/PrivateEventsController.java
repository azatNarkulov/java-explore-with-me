package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventsController {
    // TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEvents(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        // вызов сервиса
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(
            @PathVariable long userId
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void getEventById(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        // вызов сервиса
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(
            @PathVariable long userId,
            @PathVariable long eventId
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public void getEventRequests(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        // вызов сервиса
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(
            @PathVariable long userId,
            @PathVariable long eventId
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }
}
