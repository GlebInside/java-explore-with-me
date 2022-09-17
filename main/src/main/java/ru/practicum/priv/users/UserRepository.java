package ru.practicum.priv.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.users.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {
    Collection<User> findBySubscribersId(int subscriberId);

}
