package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.exception.model.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toEntity(newCategoryDto)));
    }

    @Override
    public CategoryDto readCategory(Long categoryId) {
        return CategoryMapper.toDto(categoryRepository.findById(categoryId).orElseThrow(()
                -> new EntityNotFoundException("category", categoryId)));
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }

    @Override
    public List<CategoryDto> getAllCategories(PageRequest pageRequest) {
        return null;
    }
}
