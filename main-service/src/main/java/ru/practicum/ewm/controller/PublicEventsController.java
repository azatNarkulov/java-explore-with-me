package ru.practicum.ewm.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController("events")
public class PublicEventsController {
    // TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categoriesId,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        // вызов сервиса
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void getEventById(
            @PathVariable long id
    ) {
        // вызов сервиса
    }
}
