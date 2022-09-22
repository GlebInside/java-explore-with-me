package ru.practicum.admin.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.categories.CategoryMapper;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.categories.storage.CategoryRepository;
import ru.practicum.dto.CategoryDto;
import ru.practicum.exception.BadRequestException;
import ru.practicum.pub.event.service.PublicEventService;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PublicEventService eventService;

    @Transactional
    public CategoryDto addNew(CategoryDto categoryDto) {
        var category = CategoryMapper.fromDto(categoryDto);
        var added = categoryRepository.saveAndFlush(category);
        return CategoryMapper.fromModel(added);
    }

    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        var category = categoryRepository.getReferenceById(categoryDto.getId());
        category.setName(categoryDto.getName());
        return CategoryMapper.fromModel(category);
    }

    @Transactional
    public void delete(int catId) {
        var events = eventService.countByCategoryId(catId);
        categoryRepository.delete(categoryRepository.getReferenceById(catId));
    }

    public Category getById(int id) {
        return categoryRepository.getReferenceById(id);
    }
}
