package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/compilations")
@RequiredArgsConstructor
public class PublicCompilationsController {
    // TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getCompilation(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        // вызов сервиса
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public void getCompilationById(@PathVariable long compId) {
        // вызов сервиса
    }
}
