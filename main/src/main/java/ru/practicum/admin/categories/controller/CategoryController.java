package ru.practicum.admin.categories.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.categories.dto.CategoryDto;
import ru.practicum.admin.categories.service.CategoryService;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto addNewCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return categoryService.addNew(categoryDto);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return categoryService.update(categoryDto);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        categoryService.delete(catId);
    }
}
