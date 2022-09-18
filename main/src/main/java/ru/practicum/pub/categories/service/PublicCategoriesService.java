package ru.practicum.pub.categories.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.admin.categories.CategoryMapper;
import ru.practicum.dto.CategoryDto;
import ru.practicum.admin.categories.storage.CategoryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PublicCategoriesService {

    private final CategoryRepository repository;

    public Collection<CategoryDto> find(int from, int size) {
        return repository.findAll(PageRequest.of(from, size)).stream().map(CategoryMapper::fromModel).collect(Collectors.toList());
    }

    public CategoryDto getById(int categoryId) {
        return CategoryMapper.fromModel(repository.getReferenceById(categoryId));
    }
}
