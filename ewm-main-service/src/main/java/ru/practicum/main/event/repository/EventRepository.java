package ru.practicum.main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.utils.CommonConstants;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    long countByCategoryId(Long categoryId);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long eventId);

    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    Set<Event> getByIdIn(Collection<Long> ids);

    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getByInitiatorIdOrderByEventDateDesc(Long id, Pageable pageable);

    @Query("SELECT e FROM Event e where (?1 is null or e.initiator.id in ?1) and (?2 is null or e.state in ?2) " +
            "and (?3 is null or e.category.id in ?3) and e.eventDate >= ?4 and e.eventDate <= ?5 order by e.id desc ")
    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getEventsForAdmin(List<Long> users, List<CommonConstants.EventState> states, List<Long> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("SELECT e from Event e where (?1 is null or e.category.id in ?1)")
    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getEventsForUser(List<Long> categories, Pageable pageable);

    @Query("SELECT e from Event e where (UPPER(e.annotation) like UPPER(CONCAT('%',?1,'%')) or UPPER(e.description) " +
            "like UPPER(CONCAT('%',?1,'%')) or ?1 is null ) and e.state = 'PUBLISHED' and (?2 is null or e.category.id in ?2) " +
            "and e.paid = ?3 and e.eventDate > ?4 and e.participants.size < e.participantLimit and e.eventDate <= ?5")
    @EntityGraph(value = Event.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Event> getAvailableEventsForUser(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("SELECT e from Event e where e.id = ?1 and e.state = 'PUBLISHED'")
    Optional<Event> findByIdAndPublished(Long eventId);
}
