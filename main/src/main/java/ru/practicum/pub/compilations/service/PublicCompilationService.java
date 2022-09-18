package ru.practicum.pub.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.compilations.CompilationMapper;
import ru.practicum.admin.compilations.storage.CompilationRepository;
import ru.practicum.dto.CompilationDto;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PublicCompilationService {

    private final CompilationRepository repository;

    public Collection<CompilationDto> find(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return repository.findAll(PageRequest.of(from, size)).stream().map(CompilationMapper::fromModel).collect(Collectors.toList());
        }
        return repository.findByPinned(pinned, PageRequest.of(from, size)).stream().map(CompilationMapper::fromModel).collect(Collectors.toList());
    }

    public CompilationDto getById(int compilationId) {
        return CompilationMapper.fromModel(repository.getReferenceById(compilationId));
    }
}
