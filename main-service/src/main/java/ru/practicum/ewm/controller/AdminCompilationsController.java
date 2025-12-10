package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {
    // TODO: добавить сервис

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompilation(
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(
            @PathVariable long compId
    ) {
        // вызов сервиса
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompilation(
            @PathVariable long compId
            // TODO: добавить dto в @RequestBody
    ) {
        // вызов сервиса
    }
}
