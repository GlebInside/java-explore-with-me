package ru.practicum.pub.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.categories.CategoryMapper;
import ru.practicum.admin.categories.storage.CategoryRepository;
import ru.practicum.dto.CategoryDto;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PublicCategoriesService {

    private final CategoryRepository repository;

    public Collection<CategoryDto> find(int from, int size) {
        return repository.findAll(PageRequest.of(from, size)).stream().map(CategoryMapper::fromModel).collect(Collectors.toList());
    }

    public CategoryDto getById(int categoryId) {
        return CategoryMapper.fromModel(repository.getReferenceById(categoryId));
    }
}
