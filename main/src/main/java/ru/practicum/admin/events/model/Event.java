package ru.practicum.admin.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.users.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 2000)
    @Size(min = 20, max = 2000)
    private String annotation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private int confirmedRequests;
    @Column(insertable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(length = 7000)
    @Size(min = 20, max = 7000)
    private String description;
    private LocalDateTime eventDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;
    @Column(length = 120)
    @Size(min = 3, max = 120)
    private String title;
    @Column(columnDefinition = "int default 0")
    private int views;
    private int lat;
    private int lon;
}
