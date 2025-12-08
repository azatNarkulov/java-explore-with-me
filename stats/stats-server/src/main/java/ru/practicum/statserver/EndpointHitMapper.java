package ru.practicum.statserver;

import org.springframework.stereotype.Component;
import ru.practicum.statdto.EndpointHitDto;

@Component
public class EndpointHitMapper {

    public EndpointHitDto toDto(EndpointHit entity) {
        return new EndpointHitDto(
                entity.getId(),
                entity.getApp(),
                entity.getUri(),
                entity.getIp(),
                entity.getTimestamp()
        );
    }

    public EndpointHit toEntity(EndpointHitDto dto) {
        return new EndpointHit(
                dto.getApp(),
                dto.getUri(),
                dto.getIp(),
                dto.getTimestamp()
        );
    }
}
