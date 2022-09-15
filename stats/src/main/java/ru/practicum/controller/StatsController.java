package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@Slf4j
@AllArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public void hit(@RequestBody EndpointHit dto) {
        statsService.saveHit(dto);
    }

    @GetMapping("/stats")
    public Collection<ViewStats> getStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam  @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end, @RequestParam(required = false) String[] uris, @RequestParam(defaultValue = "false") boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }
}
