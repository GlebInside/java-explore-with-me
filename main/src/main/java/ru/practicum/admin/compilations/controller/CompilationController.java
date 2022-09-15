package ru.practicum.admin.compilations.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.dto.NewCompilationDto;
import ru.practicum.admin.compilations.service.CompilationService;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@AllArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;
    @PostMapping
    public CompilationDto addNew(@RequestBody NewCompilationDto dto) {
        try {
            return compilationService.addNew(dto);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @PatchMapping("/{compilationId}/events/{eventId}")
    public CompilationDto addEvent(@PathVariable int compilationId, @PathVariable int eventId) {
        try {
            return compilationService.addEvent(compilationId, eventId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
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
