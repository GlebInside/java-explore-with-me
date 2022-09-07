package ru.practicum.admin.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.admin.categories.Category;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdminUpdateEventRequest {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private LocalDateTime eventDate;
    //    private Location location;//почему этого поля нет в Event? Нельзя назначить типом
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private String title;
}
