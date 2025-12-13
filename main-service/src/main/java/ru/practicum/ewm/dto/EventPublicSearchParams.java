package ru.practicum.ewm.dto;

import lombok.Data;
import ru.practicum.ewm.enums.EventSort;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventPublicSearchParams {

    private String text;
    private List<Long> categories;
    private Boolean paid;

    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable;
    private EventSort sort;

    private int from;
    private int size;
}
