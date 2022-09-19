package ru.practicum.priv.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.categories.storage.CategoryRepository;
import ru.practicum.admin.events.EventMapper;
import ru.practicum.admin.events.model.State;
import ru.practicum.admin.users.model.User;
import ru.practicum.admin.users.storage.AdminUserRepository;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.priv.events.dto.UpdateEventRequest;
import ru.practicum.priv.events.storage.UserEventRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserEventService {

    private final AdminUserRepository adminUserRepository;
    private final CategoryRepository categoryRepository;
    private final UserEventRepository repository;

    @Transactional
    public EventFullDto addNew(int userId, NewEventDto dto) {
        var model = EventMapper.createFromDto(dto, adminUserRepository.getReferenceById(userId), categoryRepository.getReferenceById(dto.getCategory()));
        var added = repository.saveAndFlush(model);
        return EventMapper.toFullDto(added);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("missing item with id " + id);
        }
        var model = repository.getReferenceById(id);
        repository.delete(model);
    }

    public Collection<EventShortDto> get(int userId, int from, int size) {
        return repository.findByInitiator(adminUserRepository.getReferenceById(userId), PageRequest.of(from, size)).stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Transactional
    public EventFullDto update(int userId, UpdateEventRequest dto) {
        var model = repository.getReferenceById(dto.getEventId());
        if (model.getInitiator().getId() != userId) {
            throw new NotFoundException("this event doesn't belong to this user");
        }
        Category category = null;
        if (dto.getCategory() != null) {
            category = categoryRepository.getReferenceById(dto.getCategory());
        }
        EventMapper.updateFromRequest(dto, model, category);
        return EventMapper.toFullDto(repository.saveAndFlush(model));
    }

    public EventFullDto get(int userId, int eventId) {
        var model = repository.getReferenceById(eventId);
        if (model.getInitiator().getId() != userId) {
            throw new NotFoundException("this event doesn't belong to this user");
        }
        return EventMapper.toFullDto(model);
    }

    public Collection<EventShortDto> getActualEvents(User initiator) {
        return repository.findByInitiator(initiator, null).stream()
                .filter(e -> e.getState() == State.PUBLISHED)
                .filter(e -> e.getEventDate().isAfter(LocalDateTime.now()))
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }
}
