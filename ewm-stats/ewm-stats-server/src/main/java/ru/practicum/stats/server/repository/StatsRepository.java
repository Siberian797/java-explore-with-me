package ru.practicum.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.server.model.EndpointHit;
import ru.practicum.stats.server.model.StatUnit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.stats.server.model.StatUnit(h.app, h.uri, count(h.ip)) " +
            "FROM EndpointHit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.ip) DESC")
    List<StatUnit> findByDate(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.stats.server.model.StatUnit(h.app, h.uri, count(h.ip)) " +
            "FROM EndpointHit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND h.uri in ?3 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.ip) DESC")
    List<StatUnit> findByDateAndIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.stats.server.model.StatUnit(h.app, h.uri, count(distinct h.ip)) " +
            "FROM EndpointHit h " +
            "WHERE h.timestamp BETWEEN ?1 and ?2 " +
            "AND h.uri in ?3 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.ip) DESC")
    List<StatUnit> findByDateAndUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
