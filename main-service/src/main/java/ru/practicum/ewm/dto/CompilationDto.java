package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
//    private Set<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
