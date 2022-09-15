package ru.practicum.admin.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.admin.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
//    @JoinColumn(name = "event_id")
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "event_id"), joinColumns = @JoinColumn(name = "compilation_id"), uniqueConstraints = {@UniqueConstraint(name = "unique_compilation_event", columnNames = {"event_id", "compilation_id"})})
    private List<Event> events;
    private Boolean pinned;
    private String title;
}
