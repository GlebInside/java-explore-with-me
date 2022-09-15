package ru.practicum.admin.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.admin.events.dto.EventShortDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    private int id;
    private EventShortDto[] events;
    private Boolean pinned;
    private String title;
}
