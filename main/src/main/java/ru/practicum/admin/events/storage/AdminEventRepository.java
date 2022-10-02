package ru.practicum.admin.events.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.admin.events.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface AdminEventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "select * from Event e where" +
            " ((:initiatorsProvided = false ) or (e.initiator_id in (:initiators)))" +
            " and ((:statesProvided = false ) or (e.state in (:states)))" +
            " and ((:categoriesProvided = false ) or (e.category_id in (:categories)))" +
            " and ((:rangeStartProvided = false) or (e.event_date >= :rangeStart))" +
            " and ((:rangeEndProvided = false) or (e.event_date <= :rangeEnd))" +
            "", nativeQuery = true)
    Collection<Event> find(
            boolean initiatorsProvided, int[] initiators,
            boolean statesProvided, List<String> states,
            boolean categoriesProvided, int[] categories,
            boolean rangeStartProvided, LocalDateTime rangeStart,
            boolean rangeEndProvided, LocalDateTime rangeEnd, PageRequest of);
}
