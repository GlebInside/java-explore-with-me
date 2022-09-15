package ru.practicum.priv.events.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.events.dto.EventFullDto;
import ru.practicum.admin.events.dto.EventShortDto;
import ru.practicum.admin.events.dto.NewEventDto;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.priv.events.dto.UpdateEventRequest;
import ru.practicum.priv.events.service.UserEventService;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.service.RequestService;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserEventController {

    private final UserEventService userEventService;
    private final RequestService requestService;

    @PostMapping("/{userId}/events")
    public EventFullDto addNew(@PathVariable int userId, @RequestBody NewEventDto dto) {
        try {
            return userEventService.addNew(userId, dto);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto update(@PathVariable int userId, @RequestBody UpdateEventRequest dto) {
        try {
            return userEventService.update(userId, dto);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/{userId}/events")
    public Collection<EventShortDto> getEvents(@PathVariable int userId, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") Integer size) {
        return userEventService.get(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable int userId, @PathVariable int eventId) {
        return userEventService.get(userId, eventId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userEventService.delete(id);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@RequestBody UpdateEventRequest dto, @PathVariable int userId, @PathVariable int eventId) {
        try {
            dto.setEventId(eventId);
            return userEventService.update(userId, dto);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
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
