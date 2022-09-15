package ru.practicum.admin.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    private int[] events;
    private Boolean pinned;
    private String title;
}
