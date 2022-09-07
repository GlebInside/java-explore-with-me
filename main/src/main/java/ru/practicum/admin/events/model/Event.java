package ru.practicum.admin.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.admin.categories.Category;
import ru.practicum.admin.users.User;
import ru.practicum.admin.users.UserShortDto.UserShortDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;
    private String annotation;
    @OneToOne//не уверен в типах связей
    @JoinColumn(name = "category_id")
    private Category category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    @OneToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;//в схеме указан UserShortDto, но его нельзя выбрать в качестве типа
    private Boolean paid;
    @Value("${0}")//set default, not sure if it works
    private int participantLimit;
    private LocalDateTime publishedOn;
    @Value("${true}")
    private Boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
