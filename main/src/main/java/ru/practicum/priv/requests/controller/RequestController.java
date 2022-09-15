package ru.practicum.priv.requests.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.service.RequestService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    private Collection<ParticipationRequestDto> getAllRequestsByUser(@PathVariable int userId) {
        return requestService.getAllRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    private ParticipationRequestDto addNew(@PathVariable int userId, @RequestParam int eventId) {
        return requestService.addNew(eventId, userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    private ParticipationRequestDto cancel(@PathVariable int userId, @PathVariable int requestId) {
        return requestService.cancel(requestId, userId);
    }
}
