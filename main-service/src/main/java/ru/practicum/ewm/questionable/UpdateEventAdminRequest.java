package ru.practicum.ewm.questionable;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.enumtypes.StateActionAdmin;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateEventAdminRequest {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private StateActionAdmin stateAction;
    private String title;
}
