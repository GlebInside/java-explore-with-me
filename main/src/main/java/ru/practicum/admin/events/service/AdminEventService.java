package ru.practicum.admin.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.categories.service.CategoryService;
import ru.practicum.admin.events.EventMapper;
import ru.practicum.admin.events.dto.AdminUpdateEventRequest;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.events.model.State;
import ru.practicum.admin.events.storage.AdminEventRepository;
import ru.practicum.dto.EventFullDto;
import ru.practicum.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminEventService {

    private final AdminEventRepository eventRepository;
    private final CategoryService categoryService;

    public Collection<EventFullDto> getAllEvents() {
        return eventRepository.findAll().stream().sorted(Comparator.comparing(Event::getId).reversed()).map(EventMapper::toFullDto).collect(Collectors.toList());
    }

    public Event getById(int eventId) {
        return eventRepository.getReferenceById(eventId);
    }

    @Transactional
    public EventFullDto update(int eventId, AdminUpdateEventRequest dto) {
        var model = eventRepository.getReferenceById(eventId);
        model = EventMapper.updateFromAdminRequest(model, dto, categoryService.getById(dto.getCategory()));
        return EventMapper.toFullDto(eventRepository.saveAndFlush(model));
    }

    @Transactional
    public EventFullDto publish(int eventId) {
        var model = eventRepository.getReferenceById(eventId);
        model.setState(State.PUBLISHED);
        model.setPublishedOn(LocalDateTime.now());
        return EventMapper.toFullDto(eventRepository.saveAndFlush(model));
    }

    @Transactional
    public EventFullDto reject(int eventId) {
        var model = eventRepository.getReferenceById(eventId);
        if (model.getState() != State.PENDING) {
            throw new BadRequestException("Only pending or canceled events can be changed");
        }
        model.setState(State.CANCELED);
        return EventMapper.toFullDto(eventRepository.saveAndFlush(model));
    }
}
