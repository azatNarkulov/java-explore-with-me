package ru.practicum.ewm.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventPublicSearchParams {

    private String text;
    private List<Long> categories;
    private Boolean paid;

    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable = false;
    private String sort;

    private int from = 0;
    private int size = 10;
}
