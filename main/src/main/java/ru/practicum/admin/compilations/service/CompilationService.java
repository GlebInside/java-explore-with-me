package ru.practicum.admin.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.compilations.CompilationMapper;
import ru.practicum.admin.compilations.dto.NewCompilationDto;
import ru.practicum.admin.compilations.storage.CompilationRepository;
import ru.practicum.dto.CompilationDto;
import ru.practicum.pub.event.storage.PublicEventRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompilationService {

    private final CompilationRepository repository;
    private final PublicEventRepository publicEventRepository;

    @Transactional
    public CompilationDto addNew(NewCompilationDto dto) {
        var events = new HashSet<>(publicEventRepository.findAllById(dto.getEvents()));
        var model = CompilationMapper.createFromDto(dto, events);
        var added = repository.saveAndFlush(model);
        return CompilationMapper.fromModel(added);
    }

    @Transactional
    public void delete(int id) {
        var model = repository.getReferenceById(id);
        repository.delete(model);
    }

    @Transactional
    public void deleteEvent(int id, int eventId) {
        var model = repository.getReferenceById(id);
        var events = model.getEvents();
        var filtered = events.stream().filter(e -> e.getId() != eventId).collect(Collectors.toSet());
        model.setEvents(filtered);
        repository.saveAndFlush(model);
    }

    @Transactional
    public CompilationDto addEvent(int id, int eventId) {
        var event = publicEventRepository.getReferenceById(eventId);
        var model = repository.getReferenceById(id);
        model.getEvents().add(event);
        return CompilationMapper.fromModel(repository.saveAndFlush(model));
    }

    @Transactional
    public CompilationDto pin(int compilationId) {
        return setPinned(compilationId, true);
    }

    @Transactional
    public CompilationDto unpin(int compilationId) {
        return setPinned(compilationId, false);
    }

    private CompilationDto setPinned(int compilationId, boolean pinned) {
        var model = repository.getReferenceById(compilationId);
        model.setPinned(pinned);
        return CompilationMapper.fromModel(model);
    }
}
