package ru.practicum.ewm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.questionable.Location;
import ru.practicum.ewm.enumtypes.StateActionUser;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {
    private Long id;
    private String title;
    private String annotation;
    private String description;
    private Long category;
    private LocalDateTime createdOn; // должно быть?
    private LocalDateTime eventDate;
    private LocalDateTime publishedOn; // должно быть?
    private Location location;
    private Long categoryId; // должно быть?
    private Long initiatorId; // должно быть?
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private StateActionUser stateAction; // или тут StateActionAdmin?
    private Long confirmedRequests;
    private Long views;
}
