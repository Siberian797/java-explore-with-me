package ru.practicum.main.compilation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.main.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Table(name = "compilations")
@Getter
@Setter
@Builder
@Entity
@NamedEntityGraph(
        name = Compilation.GRAPH_EVENT,
        attributeNodes = @NamedAttributeNode("events")
)
public class Compilation {
    public static final String GRAPH_EVENT = "graph.Compilation.events";

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id")
    )
    @ToString.Exclude
    private Set<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 512)
    private String title;
    @Column(nullable = false)
    private Boolean pinned;
}
