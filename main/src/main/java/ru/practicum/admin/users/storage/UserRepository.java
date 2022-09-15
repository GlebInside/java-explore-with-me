package ru.practicum.admin.users.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.users.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
