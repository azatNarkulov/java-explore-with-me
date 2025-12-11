package ru.practicum.ewm.questionable;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateCompilationRequest {
    private Set<Long> events; // мб List?
    private Boolean pinned;
    private String title;
}
