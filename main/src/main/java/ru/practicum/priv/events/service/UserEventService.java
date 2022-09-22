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
import ru.practicum.exception.BadRequestException;
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
        if (dto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Event date is too early");
        }
        var model = EventMapper.createFromDto(dto, adminUserRepository.getReferenceById(userId), categoryRepository.getReferenceById(dto.getCategory()));
        var added = repository.saveAndFlush(model);
        return EventMapper.toFullDto(added);
    }

    public Collection<EventShortDto> get(int userId, int from, int size) {
        return repository.findByInitiator(adminUserRepository.getReferenceById(userId), PageRequest.of(from, size)).stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Transactional
    public EventFullDto update(int userId, UpdateEventRequest dto) {
        var model = repository.getReferenceById(dto.getEventId());
        if (model.getState() != State.CANCELED && model.getState() != State.PENDING) {
            throw new BadRequestException("Bad status");
        }
        if (model.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Wrong event date");
        }
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
}
