package ru.practicum.admin.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.admin.categories.Category;
import ru.practicum.admin.users.UserShortDto.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private String annotation;
    private Category category;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private int id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private int views;

}
