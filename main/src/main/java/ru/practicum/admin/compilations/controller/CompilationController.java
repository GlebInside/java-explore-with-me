package ru.practicum.admin.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.admin.compilations.dto.NewCompilationDto;
import ru.practicum.admin.compilations.service.CompilationService;
import ru.practicum.exception.BadRequestException;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addNew(@Valid @RequestBody NewCompilationDto dto) {
            return compilationService.addNew(dto);
    }

    @PatchMapping("/{compilationId}/events/{eventId}")
    public CompilationDto addEvent(@PathVariable int compilationId, @PathVariable int eventId) {
        return compilationService.addEvent(compilationId, eventId);
    }

    @PatchMapping("/{compilationId}/pin")
    public CompilationDto pin(@PathVariable int compilationId) {
        return compilationService.pin(compilationId);
    }

    @DeleteMapping("/{compilationId}/pin")
    public CompilationDto unpin(@PathVariable int compilationId) {
        return compilationService.unpin(compilationId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        compilationService.delete(id);
    }

    @DeleteMapping("/{id}/events/{eventId}")
    public void deleteEvent(@PathVariable int id, @PathVariable int eventId) {
        compilationService.deleteEvent(id, eventId);
    }
}
