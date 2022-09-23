package ru.practicum.admin.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.users.model.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdminEventRepository extends JpaRepository<Event, Integer> {
    Collection<Event> findAllByInitiatorIn(List<User> initiators);

    @Query(value = "select * from Event e where" +
//            " e.state = :published" +
//            " and ((:onlyAvailable = false) or (e.confirmed_requests < e.participant_limit))" +
//            " and ((:textProvided = false ) or (lower(e.annotation) like %:text% or lower(e.description) like %:text%))" +
            " ((:initiatorsProvided = false ) or (e.initiator_id in (:initiators)))" +
            " and ((:statesProvided = false ) or (e.state in (:states)))" +
            " and ((:categoriesProvided = false ) or (e.category_id in (:categories)))" +
//            " and ((:paidProvided = false) or (e.paid = :paid))" +
//            " and ((:rangeStartProvided = false) or (e.event_date >= :rangeStart))" +
//            " and ((:rangeEndProvided = false) or (e.event_date <= :rangeEnd))" +
            "", nativeQuery = true)
    Collection<Event> find(
            boolean initiatorsProvided, int[] initiators,
            boolean statesProvided, List<String> states,
            boolean categoriesProvided, int[] categories);
}
