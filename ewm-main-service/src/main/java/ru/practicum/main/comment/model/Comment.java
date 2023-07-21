package ru.practicum.main.comment.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@Setter
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "event_id")
    private Long eventId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false, name = "commentator_id")
    private Long commentatorId;
}
