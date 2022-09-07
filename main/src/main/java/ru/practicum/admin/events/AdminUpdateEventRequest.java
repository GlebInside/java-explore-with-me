package ru.practicum.admin.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdminUpdateEventRequest {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

}
