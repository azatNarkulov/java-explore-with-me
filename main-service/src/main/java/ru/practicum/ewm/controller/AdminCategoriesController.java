package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoriesController {
    // TODO: добавить сервис

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        // вызов сервиса
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(
            @PathVariable long catId
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }
}
