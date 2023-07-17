package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.model.EntityConflictException;
import ru.practicum.main.exception.model.EntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toEntity(newCategoryDto)));
    }

    @Override
    public CategoryDto readCategory(Long categoryId) {
        return CategoryMapper.toDto(categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("category", categoryId)));
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("category", categoryId));
        if (Objects.nonNull(categoryRepository.findByName(newCategoryDto.getName())) && !category.getName().equals(newCategoryDto.getName())) {
            throw new EntityConflictException("category", categoryId);
        }
        category.setName(newCategoryDto.getName());

        return CategoryMapper.toDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("category", categoryId));

        if (eventRepository.countByCategoryId(categoryId) > 0L) {
            throw new EntityConflictException("category", categoryId);
        }

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).getContent().stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
