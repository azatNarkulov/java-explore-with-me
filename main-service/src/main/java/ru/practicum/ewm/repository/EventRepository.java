package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Page<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Page<Event> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> findAllByIdIn(Collection<Long> ids);

    Optional<Event> findByIdAndState(Long id, EventState state);
}
