package ru.practicum.priv.events.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.categories.service.CategoryService;
import ru.practicum.admin.events.EventMapper;
import ru.practicum.admin.events.dto.EventFullDto;
import ru.practicum.admin.events.dto.EventShortDto;
import ru.practicum.admin.events.dto.NewEventDto;
import ru.practicum.admin.users.service.UserService;
import ru.practicum.exception.NotFoundException;
import ru.practicum.priv.events.dto.UpdateEventRequest;
import ru.practicum.priv.events.storage.UserEventRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserEventService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final UserEventRepository repository;
    public EventFullDto addNew(int userId, NewEventDto dto) {
        var model = EventMapper.createFromDto(dto, userService.getById(userId), categoryService.getById(dto.getCategory()));
        var added = repository.saveAndFlush(model);
        return EventMapper.toFullDto(added);
    }

    public void delete(int id) {
        if(!repository.existsById(id)) {
            throw new NotFoundException("missing item with id " + id);
        }
        var model = repository.getReferenceById(id);
        repository.delete(model);
    }

    public Collection<EventShortDto> get(int userId, int from, int size) {
       return repository.findByInitiator(userService.getById(userId), PageRequest.of(from, size)).stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    public EventFullDto update(int userId, UpdateEventRequest dto) {
        var model = repository.getReferenceById(dto.getEventId());
        if(model.getInitiator().getId() != userId) {
            throw new NotFoundException("this event doesn't belong to this user");
        }
        Category category = null;
        if(dto.getCategory() != null) {
            category = categoryService.getById(dto.getCategory());
        }
        EventMapper.updateFromRequest(dto, model, category);
        return EventMapper.toFullDto(repository.saveAndFlush(model));
    }

    public EventFullDto get(int userId, int eventId) {
        var model = repository.getReferenceById(eventId);
        if(model.getInitiator().getId() != userId) {
            throw new NotFoundException("this event doesn't belong to this user");
        }
        return EventMapper.toFullDto(model);
    }
}
