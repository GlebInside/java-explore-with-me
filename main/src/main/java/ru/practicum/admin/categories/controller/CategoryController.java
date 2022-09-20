package ru.practicum.admin.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.Update;
import ru.practicum.admin.categories.service.CategoryService;
import ru.practicum.dto.CategoryDto;
import ru.practicum.exception.BadRequestException;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto addNewCategory(@Validated(Create.class) @RequestBody CategoryDto categoryDto) {
        try {
            return categoryService.addNew(categoryDto);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PatchMapping
    public CategoryDto updateCategory(@Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        categoryService.delete(catId);
    }
}
