package ru.practicum.admin.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    private String name;
    @ManyToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "subscriber_id"), joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {@UniqueConstraint(name = "unique_user_subscriber", columnNames = {"user_id", "subscriber_id"})})
    private List<User> subscribers;
}
