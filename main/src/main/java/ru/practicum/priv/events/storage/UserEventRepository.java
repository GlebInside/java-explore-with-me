package ru.practicum.priv.events.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.events.model.State;
import ru.practicum.admin.users.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

public interface UserEventRepository extends JpaRepository<Event, Integer> {
    Collection<Event> findByInitiator(User initiator, PageRequest of);

    Collection<Event> findByInitiatorAndStateAndEventDateIsAfter(User initiator, State published, LocalDateTime now);
}
