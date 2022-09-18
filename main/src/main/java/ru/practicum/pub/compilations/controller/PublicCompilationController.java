package ru.practicum.pub.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.pub.compilations.service.PublicCompilationService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
public class PublicCompilationController {

    private final PublicCompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> find(@RequestParam(required = false) Boolean pinned, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
        return compilationService.find(pinned, from, size);
    }

    @GetMapping("/{compilationId}")
    public CompilationDto findById(@PathVariable int compilationId) {
        return compilationService.getById(compilationId);
    }

}
