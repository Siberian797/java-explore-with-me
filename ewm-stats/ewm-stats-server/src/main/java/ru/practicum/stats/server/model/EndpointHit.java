package ru.practicum.stats.server.model;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "stats")
@Entity
@Builder
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
