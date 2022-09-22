package ru.practicum.pub.event.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.admin.events.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PublicEventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select * from Event e where" +
            " e.state = :published" +
            " and ((:onlyAvailable = false) or (e.confirmed_requests < e.participant_limit))" +
            " and ((:textProvided = false ) or (lower(e.annotation) like %:text% or lower(e.description) like %:text%))" +
            " and ((:categoriesProvided = false ) or (e.category_id in (:categories)))" +
            " and ((:paidProvided = false) or (e.paid = :paid))" +
            " and ((:rangeStartProvided = false) or (e.event_date >= :rangeStart))" +
            " and ((:rangeEndProvided = false) or (e.event_date <= :rangeEnd))" +
            "", nativeQuery = true)
    Collection<Event> find(int published, boolean onlyAvailable, boolean textProvided, String text, boolean categoriesProvided, int[] categories, boolean paidProvided, Boolean paid, boolean rangeStartProvided, LocalDateTime rangeStart, boolean rangeEndProvided, LocalDateTime rangeEnd, PageRequest pageRequest);

    int countByCategoryId(int categoryId);

}
