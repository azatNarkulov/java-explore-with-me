package ru.practicum.ewm.service.category;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    // Admin
    CategoryDto create(NewCategoryDto dto);

    void delete(Long id);

    CategoryDto update(Long id, CategoryDto dto);

    // Public
    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long id);
}
