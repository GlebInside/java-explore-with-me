package ru.practicum.admin.compilations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.admin.compilations.dto.NewCompilationDto;
import ru.practicum.admin.compilations.model.Compilation;
import ru.practicum.admin.events.EventMapper;
import ru.practicum.admin.events.model.Event;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {
    public static Compilation createFromDto(NewCompilationDto dto, Set<Event> events) {
        var model = new Compilation();
        model.setEvents(events);
        model.setPinned(dto.isPinned());
        model.setTitle(dto.getTitle());
        return model;
    }

    public static CompilationDto fromModel(Compilation model) {
        return new CompilationDto(model.getId(), model.getEvents().stream().map(EventMapper::toShortDto).toArray(EventShortDto[]::new), model.isPinned(), model.getTitle());
    }
}
