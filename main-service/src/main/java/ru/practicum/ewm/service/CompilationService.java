package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto dto);

    void delete(Long id);

    CompilationDto update(Long id, UpdateCompilationRequest dto);

    List<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(Long id);
}
