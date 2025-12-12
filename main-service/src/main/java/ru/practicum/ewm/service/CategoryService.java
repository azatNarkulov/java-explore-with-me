package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryDto create(NewCategoryDto dto);

    void delete(Long id);

    CategoryDto update(Long id, Category dto);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long id);
}
