package ru.practicum.admin.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.admin.events.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
