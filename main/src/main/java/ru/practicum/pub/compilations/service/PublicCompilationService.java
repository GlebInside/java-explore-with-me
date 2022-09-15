package ru.practicum.pub.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.admin.compilations.CompilationMapper;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.storage.CompilationRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PublicCompilationService {

    private final CompilationRepository repository;

    public Collection<CompilationDto> find(Boolean pinned, int from, int size) {
        if(pinned == null) {
            return repository.findAll(PageRequest.of(from, size)).stream().map(CompilationMapper::fromModel).collect(Collectors.toList());
        }
        return repository.findByPinned(pinned, PageRequest.of(from, size)).stream().map(CompilationMapper::fromModel).collect(Collectors.toList());
    }

    public CompilationDto getById(int compilationId) {
        return CompilationMapper.fromModel(repository.getReferenceById(compilationId));
    }
}
