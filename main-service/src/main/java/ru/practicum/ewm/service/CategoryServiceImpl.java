package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto dto) {
        String name = dto.getName();

        if (categoryRepository.existsByName(name)) {
            throw new ConflictException("Категория с таким именем уже существует");
        }

        Category category = new Category();
        category.setName(name);

        return mapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = findById(id);

        if (eventRepository.findByCategoryId(id, PageRequest.of(0, 1)).hasContent()) {
            throw new ConflictException("Нельзя удалить категорию с привязанными событиями");
        }

        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = findById(id);

        String newName = dto.getName();
        if (!category.getName().equals(newName) && categoryRepository.existsByName(newName)) {
            throw new ConflictException("Категория с таким именем уже существует");
        }

        category.setName(newName);
        return mapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(
                from / size,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );

        return categoryRepository.findAll(pageable).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return mapper.toDto(findById(id));
    }

    private Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с таким id не найдена"));
    }
}
