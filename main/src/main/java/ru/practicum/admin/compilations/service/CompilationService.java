package ru.practicum.admin.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.admin.compilations.CompilationMapper;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.dto.NewCompilationDto;
import ru.practicum.admin.compilations.storage.CompilationRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.pub.event.service.PublicEventService;

import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CompilationService {

    private final PublicEventService publicEventService;
    private final CompilationRepository repository;

    public CompilationDto addNew(NewCompilationDto dto) {
        var events = Arrays.stream(dto.getEvents())
                .mapToObj(publicEventService::getById)
                .collect(Collectors.toList());
        var model = CompilationMapper.createFromDto(dto, events);
        var added = repository.saveAndFlush(model);
        return CompilationMapper.fromModel(added);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("missing item with id " + id);
        }
        var model = repository.getReferenceById(id);
        repository.delete(model);
    }

    public void deleteEvent(int id, int eventId) {
        var model = repository.getReferenceById(id);
        var events = model.getEvents();
        var filtered = events.stream().filter(e -> e.getId() != eventId).collect(Collectors.toList());
        model.setEvents(filtered);
        repository.saveAndFlush(model);
    }

    public CompilationDto addEvent(int id, int eventId) {
        var event = publicEventService.getById(eventId);
        var model = repository.getReferenceById(id);
        model.getEvents().add(event);
        return CompilationMapper.fromModel(repository.saveAndFlush(model));
    }

    public CompilationDto pin(int compilationId) {
        return setPinned(compilationId, true);
    }

    public CompilationDto unpin(int compilationId) {
        return setPinned(compilationId, false);
    }

    public CompilationDto setPinned(int compilationId, boolean pinned) {
        var model = repository.getReferenceById(compilationId);
        model.setPinned(pinned);
        model = repository.saveAndFlush(model);
        return CompilationMapper.fromModel(model);
    }
}
