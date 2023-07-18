package ru.practicum.main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT COUNT(e) FROM Event e WHERE e.category.id = ?1")
    long countByCategoryId(Long categoryId);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long eventId);

    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    Set<Event> getByIdIn(Collection<Long> ids);

    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getByInitiatorIdOrderByEventDateDesc(Long id, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "where ((:users) is null or e.initiator.id in :users) " +
            "and ((:states) is null or e.state in :states) " +
            "and ((:categories) is null or e.category.id in :categories) " +
            "and e.eventDate >= :rangeStart " +
            "and e.eventDate <= :rangeEnd " +
            "order by e.id desc ")
    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getEventsForAdmin(
            @Param("users") List<Long> users,
            @Param("states") List<CommonConstants.EventState> states,
            @Param("categories") List<Long> categories,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("SELECT e from Event e " +
            "where ((:categories) is null or e.category.id in :categories)")
    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getEventsForUser(
            @Param("categories") List<Long> categories,
            Pageable pageable
    );

    @Query("SELECT e from Event e " +
            "where (UPPER(e.annotation) like UPPER(CONCAT('%',:text,'%')) " +
            "or UPPER(e.description) like UPPER(CONCAT('%',:text,'%')) or :text is null ) " +
            "and e.state = 'PUBLISHED' " +
            "and ((:categories) is null or e.category.id in :categories) " +
            "and e.paid = :paid " +
            "and e.eventDate > :rangeStart " +
            "and e.participants.size < e.participantLimit " +
            "and e.eventDate <= :rangeEnd")
    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getAvailableEventsForUser(
            @Param("text") String text,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable
    );


    @Query("SELECT e from Event e where e.id = :eventId and e.state = 'PUBLISHED'")
    Optional<Event> findByIdAndPublished(@Param("eventId") Long eventId);
}
