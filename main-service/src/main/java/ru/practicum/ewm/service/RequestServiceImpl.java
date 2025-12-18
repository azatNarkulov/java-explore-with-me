package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.enums.RequestStatusUpdateAction;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper mapper;

    @Override
    @Transactional
    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = findUserById(userId);

        Event event = findEventById(eventId);

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Пользователь уже организует мероприятие");
        }

        if (requestRepository.findByEventIdAndRequesterId(eventId, userId).isPresent()) {
            throw new ConflictException("Заявка уже подана");
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Событие должно быть опубликовано для подачи запроса");
        }

        long confirmedRequests = findConfirmedRequests(eventId);

        if (event.getParticipantLimit() > 0 && confirmedRequests >= event.getParticipantLimit()) {
            throw new ConflictException("Достигнут лимит участников мероприятия");
        }

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());

        if (isRequestModerationDisabled(event)) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }

        return mapper.toDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        Request request = findRequestById(requestId);

        if (!request.getRequester().getId().equals(userId)) {
            throw new ConflictException("Нельзя отменить запрос другого пользователя");
        }

        request.setStatus(RequestStatus.CANCELED);

        return mapper.toDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        findUserById(userId);

        return requestRepository.findByRequesterId(userId).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestsForInitiator(Long userId, Long eventId) {
        Event event = findEventById(eventId);

        validateAccessToEvent(event, userId);

        return requestRepository.findByEventId(eventId).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequestStatus(
            Long userId,
            Long eventId,
            EventRequestStatusUpdateRequest dto
    ) {
        Event event = findEventById(eventId);
        validateAccessToEvent(event, userId);

        if (!event.getRequestModeration()) {
            throw new ConflictException("Модерация отключена для данного мероприятия");
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Событие не опубликовано");
        }

        List<Request> requests = requestRepository.findAllById(dto.getRequestIds());

        for (Request request : requests) {
            if (!request.getEvent().getId().equals(eventId)) {
                throw new NotFoundException("Запрос не принадлежит этому событию");
            }

            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictException("Обновлять можно только запросы, ожидающие обработки");
            }
        }

        long confirmedRequests = findConfirmedRequests(eventId);

        if (dto.getStatus() == RequestStatusUpdateAction.CONFIRMED) {
            if (event.getParticipantLimit() > 0 && confirmedRequests >= event.getParticipantLimit()) {
                throw new ConflictException("Достигнут лимит участников события");
            }
        }

        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();

        for (Request request : requests) {

            if (dto.getStatus() == RequestStatusUpdateAction.CONFIRMED) {
                if (event.getParticipantLimit() > 0 && confirmedRequests >= event.getParticipantLimit()) {
                    request.setStatus(RequestStatus.REJECTED);
                    rejected.add(mapper.toDto(request));
                } else {
                    request.setStatus(RequestStatus.CONFIRMED);
                    confirmed.add(mapper.toDto(request));
                    confirmedRequests++;
                }
            } else {
                request.setStatus(RequestStatus.REJECTED);
                rejected.add(mapper.toDto(request));
            }
        }

        requestRepository.saveAll(requests);

        return new EventRequestStatusUpdateResult(confirmed, rejected);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким id не найдено"));
    }

    private Request findRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с таким id не найден"));
    }

    private void validateAccessToEvent(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Доступ есть только у организатора");
        }
    }

    private boolean isRequestModerationDisabled(Event event) {
        return !event.getRequestModeration() || event.getParticipantLimit() == 0;
    }

    private long findConfirmedRequests(Long eventId) {
        return requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
    }
}
