package ru.practicum.admin.categories;

import ru.practicum.admin.categories.dto.CategoryDto;
import ru.practicum.admin.categories.model.Category;

public class CategoryMapper {
    public static Category fromDto(CategoryDto categoryDto) {
        var category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto fromModel(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
