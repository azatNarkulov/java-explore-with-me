package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController("/admin/events")
@RequiredArgsConstructor
public class AdminEventsController {
    // TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEvents(
            @RequestParam(required = false) List<Long> usersId,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categoriesId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        // вызов сервиса
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(
            @PathVariable long eventId
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }
}
