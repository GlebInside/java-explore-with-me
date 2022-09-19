package ru.practicum.admin.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.events.dto.AdminUpdateEventRequest;
import ru.practicum.admin.events.model.State;
import ru.practicum.admin.events.service.AdminEventService;
import ru.practicum.dto.EventFullDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService eventService;

    @GetMapping
    private Collection<EventFullDto> find(
            @RequestParam(required = false) String[] users,
            @RequestParam(required = false) State[] states,
            @RequestParam(required = false) String[] categories,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
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
