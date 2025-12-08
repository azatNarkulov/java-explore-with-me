package ru.practicum.statserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statdto.ViewStatsDto;
import ru.practicum.statserver.EndpointHit;
import ru.practicum.statserver.EndpointHitMapper;
import ru.practicum.statserver.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final EndpointHitMapper mapper;

    @Override
    @Transactional
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit saved = statsRepository.save(mapper.toEntity(endpointHitDto));
        return mapper.toDto(saved);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return unique ? statsRepository.findUniqueStats(start, end, uris) : statsRepository.findStats(start, end, uris);
    }
}
