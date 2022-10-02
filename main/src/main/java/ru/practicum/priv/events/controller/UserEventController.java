package ru.practicum.priv.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.priv.events.dto.UpdateEventRequest;
import ru.practicum.priv.events.service.UserEventService;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.service.RequestService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserEventController {

    private final UserEventService userEventService;
    private final RequestService requestService;

    @PostMapping("/{userId}/events")
    public EventFullDto addNew(@PathVariable int userId, @Valid @RequestBody NewEventDto dto) {
        return userEventService.addNew(userId, dto);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto update(@PathVariable int userId, @RequestBody UpdateEventRequest dto) {
        return userEventService.update(userId, dto);
    }

    @GetMapping("/{userId}/events")
    public Collection<EventShortDto> getEvents(@PathVariable int userId, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") Integer size) {
        return userEventService.get(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable int userId, @PathVariable int eventId) {
        return userEventService.get(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable int userId, @PathVariable int eventId) {
        return userEventService.cancel(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public Collection<ParticipationRequestDto> getRequests(@PathVariable int userId, @PathVariable int eventId) {
        return requestService.getAllRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{requestId}/confirm")
    public ParticipationRequestDto confirm(@PathVariable int userId, @PathVariable int eventId, @PathVariable int requestId) {
        return requestService.confirm(userId, eventId, requestId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{requestId}/reject")
    public ParticipationRequestDto reject(@PathVariable int userId, @PathVariable int eventId, @PathVariable int requestId) {
        return requestService.reject(userId, eventId, requestId);
    }
}
