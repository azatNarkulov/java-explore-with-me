package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.enums.EventSort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventPublicSearchParams {

    private String text;
    private List<Long> categories = new ArrayList<>();
    private Boolean paid;

    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable = false;
    private EventSort sort;

    private int from = 0;
    private int size = 10;
}
