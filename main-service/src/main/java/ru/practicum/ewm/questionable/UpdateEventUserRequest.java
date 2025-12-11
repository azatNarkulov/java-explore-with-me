package ru.practicum.ewm.questionable;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.enumtypes.StateActionUser;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private StateActionUser stateAction;
    private String title;
}
