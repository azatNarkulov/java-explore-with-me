package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByRequesterId(Long requesterId);

    List<Request> findByEventId(Long eventId);

    List<Request> findByEventIdAndStatus(Long eventId, RequestStatus status);

    Optional<Request> findByEventIdAndRequesterId(Long eventId, Long requesterId);

    long countByEventIdAndStatus(Long eventId, RequestStatus status);

    @Query("SELECT r FROM Request r " +
            "JOIN r.event e " +
            "WHERE e.initiator.id = :initiatorId")
    List<Request> findByEventInitiatorId(Long initiatorId);
}
