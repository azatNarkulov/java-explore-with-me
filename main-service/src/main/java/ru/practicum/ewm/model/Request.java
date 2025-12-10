package ru.practicum.ewm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class Request {
    private Long id;
    private Long requesterId;
    private Long eventId;
    private LocalDateTime created;
//    private RequestStatus status; тут enum'чик нужен
}
