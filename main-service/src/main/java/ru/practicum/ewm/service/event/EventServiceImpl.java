package ru.practicum.ewm.service.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.model.event.EventState;
import ru.practicum.ewm.dto.request.RequestStatus;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final EventMapper mapper;
    private final StatsClient statsClient;

    @Override
    @Transactional
    public EventFullDto create(Long userId, NewEventDto dto) {
        User user = findUserById(userId);
        Category category = findCategoryById(dto.getCategory());

        validateEventDate(dto.getEventDate());

        Event event = mapper.toEntity(dto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setState(EventState.PENDING);
        event.setCreatedOn(LocalDateTime.now());

        EventFullDto fullDto = mapper.toFullDto(eventRepository.save(event), 0L);
        fullDto.setConfirmedRequests(0L);

        return fullDto;
    }

    @Override
    @Transactional
    public EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest dto) {
        Event event = findEventById(eventId);

        validateAccess(event, userId);

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Опубликованные мероприятия нельзя обновлять");
        }

        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }

        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }

        if (dto.getEventDate() != null) {
            validateEventDate(dto.getEventDate());
            event.setEventDate(dto.getEventDate());
        }

        if (dto.getCategory() != null) {
            event.setCategory(findCategoryById(dto.getCategory()));
        }

        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }

        if (dto.getStateAction() != null) {
            switch (dto.getStateAction()) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                default:
                    break;
            }
        }

        Event saved = eventRepository.save(event);

        long views = getViews(List.of(saved))
                .getOrDefault(saved.getId(), 0L);

        EventFullDto fullDto = mapper.toFullDto(saved, views);
        fullDto.setConfirmedRequests(getConfirmedRequests(eventId));
        return fullDto;
    }

    @Override
    public List<EventShortDto> getUserEvents(Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);

        Map<Long, Long> views = getViews(events);

        return events.stream()
                .map(event -> {
                    EventShortDto shortDto = mapper.toShortDto(event, views.getOrDefault(event.getId(), 0L));
                    shortDto.setConfirmedRequests(getConfirmedRequests(event.getId()));
                    return shortDto;
                })
                .toList();
    }

    @Override
    public EventFullDto getUserEventById(Long userId, Long eventId) {
        Event event = findEventById(eventId);

        validateAccess(event, userId);

        long views = getViews(List.of(event))
                .getOrDefault(eventId, 0L);

        EventFullDto fullDto = mapper.toFullDto(event, views);
        fullDto.setConfirmedRequests(getConfirmedRequests(eventId));
        return fullDto;
    }

    @Override
    public EventFullDto getPublicEventById(Long eventId, HttpServletRequest request) {
        logHit(request);

        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Мероприятие не найдено"));

        Map<Long, Long> views = getViews(List.of(event));

        EventFullDto fullDto = mapper.toFullDto(event, views.getOrDefault(eventId, 0L));
        fullDto.setConfirmedRequests(getConfirmedRequests(eventId));

        return fullDto;
    }

    @Override
    public List<EventShortDto> getPublicEvents(EventPublicSearchParams params, HttpServletRequest request) {
        log.info("getPublicEvents started with params={}", params);

        try {
            logHit(request);
        } catch (Exception e) {
            log.warn("Не удалось сохранить статистику");
        }

        if (params.getRangeStart() != null
                && params.getRangeEnd() != null
                && params.getRangeStart().isAfter(params.getRangeEnd())
        ) {
            throw new ValidationException("Начало должно быть раньше даты окончания");
        }

        int size = params.getSize();
        int from = params.getFrom();

        if (size <= 0) {
            size = 10;
        }

        if (from < 0) {
            from = 10;
        }

        Pageable pageable = PageRequest.of(from / size, size);

        List<Long> categories = params.getCategories();
        if (categories != null && categories.isEmpty()) {
            log.debug("Categories list is empty, converting to null");
            categories = null;
        }

        log.debug(
                "Calling repository with filters: text={}, categories={}, paid={}, " +
                        "rangeStart={}, rangeEnd={}, onlyAvailable={}, pageable={}",
                params.getText(),
                categories,
                params.getPaid(),
                params.getRangeStart(),
                params.getRangeEnd(),
                params.getOnlyAvailable(),
                pageable
        );

        List<Event> events;

        if (categories == null || categories.isEmpty()) {
            events = eventRepository.findAllByPublicFiltersWithoutCategories(
                    params.getText(),
                    params.getPaid(),
                    params.getRangeStart(),
                    params.getRangeEnd(),
                    "PUBLISHED",
                    pageable
            );
        } else {
            events = eventRepository.findAllByPublicFiltersWithCategories(
                    params.getText(),
                    categories,
                    params.getPaid(),
                    params.getRangeStart(),
                    params.getRangeEnd(),
                    "PUBLISHED",
                    pageable
            );
        }

        log.info("Repository returned {} events", events.size());

        if (params.getOnlyAvailable() != null && params.getOnlyAvailable()) {
            int before = events.size();
            events = events.stream()
                    .filter(event ->
                            event.getParticipantLimit() == 0 || getConfirmedRequests(event.getId())
                                    < event.getParticipantLimit())
                    .toList();

            log.debug("onlyAvailable filter applied: before={}, after={}", before, events.size());
        }

        Map<Long, Long> views = getViews(events);
        log.debug("Views loaded for {} events", views.size());

        Stream<Event> stream = events.stream();

        EventSort eventSort;

        if (params.getSort() == null || params.getSort().isBlank()) {
            eventSort = EventSort.EVENT_DATE;
        } else {
            try {
                eventSort = EventSort.valueOf(params.getSort());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid sort parameter: {}", params.getSort());
                throw new ValidationException("Указан некорректный вариант сортировки");
            }
        }

        log.warn("Sorting events by {}", eventSort);

        switch (eventSort) {
            case VIEWS:
                stream = stream.sorted(
                        Comparator.comparing(
                                event -> views.getOrDefault(event.getId(), 0L),
                                Comparator.reverseOrder()
                        )
                );
                break;
            case EVENT_DATE:
                stream = stream.sorted(
                        Comparator.comparing(Event::getEventDate)
                );
                break;
            default:
                throw new ValidationException("Указан некорректный вариант сортировки");
        }

        List<EventShortDto> result = stream
                .map(event -> {
                    EventShortDto shortDto = mapper.toShortDto(event, views.getOrDefault(event.getId(), 0L));
                    shortDto.setConfirmedRequests(getConfirmedRequests(event.getId()));
                    return shortDto;
                })
                .toList();

        log.info("getPublicEvents finished, returning {} items", result.size());
        return result;
    }

    @Override
    public List<EventFullDto> getAdminEvents(EventAdminSearchParams params) {
        Pageable pageable = PageRequest.of(
                params.getFrom() / params.getSize(),
                params.getSize()
        );

        List<EventState> states = params.getStates() == null
                ? null
                : params.getStates().stream()
                        .map(EventState::valueOf)
                        .toList();

        List<Long> categories = params.getCategories();
        if (categories != null && categories.isEmpty()) {
            categories = null;
        }

        List<Event> events = eventRepository.findAllByAdminFilters(
                params.getUsers(),
                states,
                categories,
                params.getRangeStart(),
                params.getRangeEnd(),
                pageable
        );

        Map<Long, Long> views = getViews(events);

        return events.stream()
                .map(event -> {
                    EventFullDto fullDto = mapper.toFullDto(event, views.getOrDefault(event.getId(), 0L));
                    fullDto.setConfirmedRequests(getConfirmedRequests(event.getId()));
                    return fullDto;
                })
                .toList();
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto) {
        Event event = findEventById(eventId);

        if (dto.getStateAction() != null) {
            switch (dto.getStateAction()) {
                case PUBLISH_EVENT:
                    validatePending(event, "Опубликовать можно только мероприятия, которые находятся в ожидании");
                    event.setState(EventState.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;

                case REJECT_EVENT:
                    validatePending(event, "Отклонить можно только те мероприятия, которые находятся в ожидании");
                    event.setState(EventState.CANCELED);
                    break;

                default:
                    break;
            }
        }

        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }

        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }

        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }

        if (dto.getCategory() != null) {
            event.setCategory(findCategoryById(dto.getCategory()));
        }

        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }

        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }

        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }

        Event saved = eventRepository.save(event);

        long views = getViews(List.of(saved))
                .getOrDefault(saved.getId(), 0L);

        EventFullDto fullDto = mapper.toFullDto(saved, views);
        fullDto.setConfirmedRequests(getConfirmedRequests(eventId));
        return fullDto;
    }

    private void logHit(HttpServletRequest request) {
        EndpointHitDto hit = new EndpointHitDto();
        hit.setApp("ewm-main-service");
        hit.setUri(request.getRequestURI());
        hit.setIp(request.getRemoteAddr());
        hit.setTimestamp(LocalDateTime.now());

        statsClient.saveHit(hit);
    }

    private Map<Long, Long> getViews(List<Event> events) {
        if (events.isEmpty()) {
            return Map.of();
        }

        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .toList();

        LocalDateTime start = events.stream()
                .map(event -> {
                    if (event.getPublishedOn() != null) {
                        return event.getPublishedOn();
                    } else if (event.getState() == EventState.PUBLISHED) {
                        return event.getCreatedOn();
                    } else {
                        return LocalDateTime.now();
                    }
                })
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        try {
            List<ViewStatsDto> stats = statsClient.getStats(start, LocalDateTime.now(), uris, true);

            return stats.stream()
                    .collect(Collectors.toMap(
                            stat -> Long.parseLong(stat.getUri().substring("/events/".length())),
                            ViewStatsDto::getHits
                    ));
        } catch (Exception e) {
            log.warn("Ошибка при получении статистики просмотров", e);
            return Collections.emptyMap();
        }
    }

    private long getConfirmedRequests(Long eventId) {
        return requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
    }

    private Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Мероприятие не найдено"));
    }

    private void validateAccess(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Только организатор может обновлять мероприятие");
        }
    }

    private void validateEventDate(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Время мероприятия должно быть не ранее, чем через 2 часа от текущего момента");
        }
    }

    private void validatePending(Event event, String message) {
        if (event.getState() != EventState.PENDING) {
            throw new ConflictException(message);
        }
    }
}
