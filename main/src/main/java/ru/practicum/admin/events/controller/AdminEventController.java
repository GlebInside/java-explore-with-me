package ru.practicum.admin.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.events.dto.AdminUpdateEventRequest;
import ru.practicum.admin.events.dto.EventFullDto;
import ru.practicum.admin.events.service.AdminEventService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService eventService;

    @GetMapping
    private Collection<EventFullDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PutMapping("/{eventId}")
    private EventFullDto updateEventById(@PathVariable int eventId, @RequestBody AdminUpdateEventRequest dto) {
        return eventService.update(eventId, dto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable int eventId) {
        return eventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable int eventId) {
        return eventService.reject(eventId);
    }
}
