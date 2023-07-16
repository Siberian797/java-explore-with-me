package ru.practicum.main.category.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto readCategory(Long categoryId);

    CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto);

    void deleteCategory(Long categoryId);

    List<CategoryDto> getAllCategories(PageRequest pageRequest);
}
