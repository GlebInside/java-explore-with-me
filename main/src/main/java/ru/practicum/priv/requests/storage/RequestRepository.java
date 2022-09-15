package ru.practicum.priv.requests.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.priv.requests.model.Request;

import java.util.Collection;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Collection<Request> findByRequesterId(int requesterId);

    Collection<Request> findByEventId(int eventId);
}
