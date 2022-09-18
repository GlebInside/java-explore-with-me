package ru.practicum.admin.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.practicum.admin.events.model.Event;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "event_id"), joinColumns = @JoinColumn(name = "compilation_id"), uniqueConstraints = {@UniqueConstraint(name = "unique_compilation_event", columnNames = {"event_id", "compilation_id"})})
    private List<Event> events;
    private boolean pinned;
    @Column(nullable = false)
    @Size(max = 100)
    private String title;
}
