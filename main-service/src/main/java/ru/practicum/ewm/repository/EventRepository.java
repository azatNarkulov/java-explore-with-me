package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Page<Event> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> findAllByIdIn(Collection<Long> ids);

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndState(Long id, EventState state);

    List<Event> findAllByPublicFilters(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    List<Event> findAllByAdminFilters(
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );
}
