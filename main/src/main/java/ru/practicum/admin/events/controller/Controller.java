package ru.practicum.admin.events.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.events.service.EventService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@AllArgsConstructor
public class Controller {

    private final EventService eventService;

    @GetMapping
    private Collection<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
}
