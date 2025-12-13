package ru.practicum.ewm.dto;

import lombok.Data;
import ru.practicum.ewm.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventAdminSearchParams {

    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;

    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;

    private int from;
    private int size;
}
