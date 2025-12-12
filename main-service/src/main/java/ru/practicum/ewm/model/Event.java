package ru.practicum.ewm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.enumtypes.EventState;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // или это Long?

    @Column(name = "created_on", nullable = false) // или без nullable = false?
    private LocalDateTime createdOn; // должно быть?

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Embedded
    private Location location;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private Long initiator; // или это Long?

    @Column(name = "paid", nullable = false) // или без nullable = false?
    private Boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @Column(name = "state", nullable = false)
    private EventState state;

//    @Column(name = "confirmed_requests")
//    private Long confirmedRequests;
}
