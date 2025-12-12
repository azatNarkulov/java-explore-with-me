package ru.practicum.ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.dto.*;

import java.util.List;

public interface EventService {

    EventFullDto create(Long userId, NewEventDto dto);

    EventFullDto update(Long userId, NewEventDto dto);

    List<EventShortDto> getUserEvents(Long userId, Long eventId, UpdateEventUserRequest dto);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest dto);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

//    List<EventShortDto> getPublicEvents(EventPublicSearchParams params);

    EventFullDto getPublicEventById(Long id, HttpServletRequest request);

//    List<EventFullDto> getEventsByAdmin(EventAdminSearchParams params);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto);
}
