package ru.practicum.ewm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/categories")
public class PublicCategoriesController {
// TODO: добавить сервис

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getCategories(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        // вызов сервиса
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void getCategoryById(
            @PathVariable long catId
    ) {
        // вызов сервиса
    }
}
