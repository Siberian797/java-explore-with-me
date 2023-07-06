package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable @Positive Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto updateCategory(@PathVariable @Positive Long categoryId,
                                      @RequestBody @Valid NewCategoryDto newCategoryDto) {
        return categoryService.updateCategory(categoryId, newCategoryDto);
    }
}
