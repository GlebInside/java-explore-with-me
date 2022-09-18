package ru.practicum.admin.categories;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.admin.categories.dto.CategoryDto;
import ru.practicum.admin.categories.model.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {
    public static Category fromDto(CategoryDto categoryDto) {
        var category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto fromModel(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
