package ru.practicum.pub.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.pub.categories.service.PublicCategoriesService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
public class PublicCategoriesController {

    private final PublicCategoriesService categoryService;

    @GetMapping
    public Collection<CategoryDto> find(@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
        return categoryService.find(from, size);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto findById(@PathVariable int categoryId) {
        return categoryService.getById(categoryId);
    }

}
