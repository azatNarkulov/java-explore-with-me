package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.enums.EventSort;

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
    private EventSort sort;

    private Integer from = 0;
    private Integer size = 10;
}
