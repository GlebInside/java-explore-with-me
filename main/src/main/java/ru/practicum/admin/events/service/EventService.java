package ru.practicum.admin.events.service;

import org.springframework.stereotype.Component;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.events.storage.EventRepository;

import java.util.Collection;

@Component
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Collection<Event> getAllEvents() {
       return eventRepository.findAll();
    }
}
