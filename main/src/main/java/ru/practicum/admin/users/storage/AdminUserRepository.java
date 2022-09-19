package ru.practicum.admin.users.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.admin.users.model.User;

import java.util.Collection;
import java.util.List;

public interface AdminUserRepository extends JpaRepository<User, Integer> {
    @Query("FROM User WHERE id in :ids")
    Collection<User> findAllById(List<Integer> ids, PageRequest of);
}
