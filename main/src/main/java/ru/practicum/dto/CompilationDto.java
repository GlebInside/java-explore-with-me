package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    private int id;
    private EventShortDto[] events;
    private Boolean pinned;
    private String title;
}
