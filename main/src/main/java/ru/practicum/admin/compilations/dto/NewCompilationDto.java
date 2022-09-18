package ru.practicum.admin.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    private int[] events;
    private Boolean pinned;
    @NotBlank
    private String title;
}
