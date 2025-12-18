package ru.practicum.ewm.service.compilation;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    // Admin
    CompilationDto create(NewCompilationDto dto);

    void delete(Long id);

    CompilationDto update(Long id, UpdateCompilationRequest dto);

    // Public
    List<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(Long id);
}
