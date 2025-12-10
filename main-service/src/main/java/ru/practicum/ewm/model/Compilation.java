package ru.practicum.ewm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "compilations")
public class Compilation {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<Long> eventsIds;
}
