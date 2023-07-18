package ru.practicum.main.request.model;

import lombok.*;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;
import ru.practicum.main.utils.CommonConstants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@Setter
@Getter
@Table(name = "requests")
@NoArgsConstructor
@ToString
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Column
    @Enumerated(EnumType.STRING)
    private CommonConstants.RequestStatus status;

    @Column
    private LocalDateTime created;
}
