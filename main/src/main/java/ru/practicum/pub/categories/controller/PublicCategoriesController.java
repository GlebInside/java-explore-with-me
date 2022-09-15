package ru.practicum.pub.categories.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import ru.practicum.admin.categories.dto.CategoryDto;
import ru.practicum.pub.categories.service.PublicCategoriesService;
import ru.practicum.statsClient.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@AllArgsConstructor
public class PublicCategoriesController {

    private final PublicCategoriesService categoryService;

    @GetMapping
    public Collection<CategoryDto> find(@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        return categoryService.find(from, size);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto findById(@PathVariable int categoryId) {
        return categoryService.getById(categoryId);
    }

}
