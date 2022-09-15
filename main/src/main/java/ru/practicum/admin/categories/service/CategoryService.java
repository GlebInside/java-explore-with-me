package ru.practicum.admin.categories.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.admin.categories.CategoryMapper;
import ru.practicum.admin.categories.dto.CategoryDto;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.categories.storage.CategoryRepository;

@AllArgsConstructor
@Component
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryDto addNew(CategoryDto categoryDto) {
        var category = CategoryMapper.fromDto(categoryDto);
        var added = categoryRepository.saveAndFlush(category);
        return CategoryMapper.fromModel(added);
    }

    public CategoryDto update(CategoryDto categoryDto) {
        var category = categoryRepository.getReferenceById(categoryDto.getId());
        category.setName(categoryDto.getName());
        return CategoryMapper.fromModel(categoryRepository.saveAndFlush(category));
    }

    public void delete(int catId) {
        categoryRepository.delete(categoryRepository.getReferenceById(catId));
    }

    public Category getById(int id) {
        return categoryRepository.getReferenceById(id);
    }
}
