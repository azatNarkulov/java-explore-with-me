package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.model.Category;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);
}
