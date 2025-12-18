package ru.practicum.ewm.service.event;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.dto.event.*;

import java.util.List;

public interface EventService {

    // Private
    EventFullDto create(Long userId, NewEventDto dto);

    EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest dto);

    List<EventShortDto> getUserEvents(Long userId, int from, int size);

    EventFullDto getUserEventById(Long userId, Long eventId);

    // Public
    List<EventShortDto> getPublicEvents(EventPublicSearchParams params, HttpServletRequest request);

    EventFullDto getPublicEventById(Long eventId, HttpServletRequest request);

    // Admin
    List<EventFullDto> getAdminEvents(EventAdminSearchParams params);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto);
}
