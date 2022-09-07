package ru.practicum.admin.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.admin.categories.Category;
import ru.practicum.admin.events.Location;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    private String annotation;
    private Category category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;//почему этого поля нет в Event?
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private String title;
}
