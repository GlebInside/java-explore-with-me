package ru.practicum.admin.compilations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.admin.events.dto.EventShortDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
//    private EventShortDto events;//тут так же невозможно выбрать тип Dto
    private Boolean pinned;
    private String title;
}
