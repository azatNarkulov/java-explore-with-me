package ru.practicum.ewm.controller.event;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.service.request.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;
    private final RequestService requestService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEvents(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        return eventService.getUserEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(
            @PathVariable long userId,
            @RequestBody @Valid NewEventDto dto
    ) {
        return eventService.create(userId, dto);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventById(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventUserRequest dto
    ) {
        return eventService.update(userId, eventId, dto);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getEventRequests(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        return requestService.getEventRequestsForInitiator(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateStatus(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest dto
    ) {
        return requestService.updateEventRequestStatus(userId, eventId, dto);
    }
}
