package ru.practicum.main.compilation.model;

import lombok.*;
import ru.practicum.main.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Table(name = "compilations")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = Compilation.GRAPH_EVENT,
        attributeNodes = @NamedAttributeNode("events")
)
public class Compilation {
    public static final String GRAPH_EVENT = "graph.Compilation.events";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id")
    )
    private Set<Event> events;

    @Column(nullable = false, length = 512)
    private String title;

    @Column(nullable = false)
    private Boolean pinned;
}
