package ru.practicum.pub.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.events.dto.EventFullDto;
import ru.practicum.admin.events.dto.EventShortDto;
import ru.practicum.admin.events.model.State;
import ru.practicum.pub.event.dto.EventSort;
import ru.practicum.pub.event.service.PublicEventService;
import ru.practicum.statsClient.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "events")
public class PublicEventController {

    private final PublicEventService publicEventService;
    private final StatsClient statsClient;

    @GetMapping
    public Collection<EventShortDto> getAllEventsForFiltration(@RequestParam(required = false) String text
            , @RequestParam(required = false) String[] categories
            , @RequestParam(required = false) Boolean paid
            , @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeStart
            , @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeEnd
            , @RequestParam(defaultValue = "false") boolean onlyAvailable
            , @RequestParam(required = false) EventSort eventSort
            , @RequestParam(defaultValue = "0") int from
            , @RequestParam(defaultValue = "10") int size,
                                                               HttpServletRequest request) {
        statsClient.saveHit(request.getRemoteAddr(), request.getRequestURI());
        return publicEventService.find(text, State.PUBLISHED, categories, paid, rangeStart, rangeEnd, onlyAvailable, eventSort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable int eventId, HttpServletRequest request) throws JsonProcessingException {
        statsClient.saveHit(request.getRemoteAddr(), request.getRequestURI());
        var viewStats = statsClient.getStats(LocalDateTime.of(1900, 1, 1, 0, 0), LocalDateTime.of(3000, 1, 1, 0, 0), request.getRequestURI());
        var event = publicEventService.getFullDtoById(eventId);
        event.setViews(viewStats.getHits());
        return event;
    }
}
