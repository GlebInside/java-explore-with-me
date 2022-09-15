package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stats {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
