package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto categoryDto;
    private Long confirmedRequests;
    private LocalDateTime eventDate;
    private Long id;
//    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
