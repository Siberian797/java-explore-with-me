package ru.practicum.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitRequestDto;
import ru.practicum.stats.dto.EndpointHitResponseDto;
import ru.practicum.stats.server.exception.StartAfterEndException;
import ru.practicum.stats.server.mapper.EndpointHitMapper;
import ru.practicum.stats.server.model.StatUnit;
import ru.practicum.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public void createHit(EndpointHitRequestDto endpointHitRequestDto) {
        statsRepository.save(EndpointHitMapper.toHit(endpointHitRequestDto));
    }

    @Override
    public List<EndpointHitResponseDto> getHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new StartAfterEndException();
        }

        if (uris.isEmpty()) {
            return statsRepository.findByDate(start, end).stream().map(EndpointHitMapper::toDto).collect(Collectors.toList());
        }

        List<StatUnit> statUnits = unique ?
                statsRepository.findByDateAndUniqueIp(start, end, uris) :
                statsRepository.findByDateAndIp(start, end, uris);
        return statUnits.stream().map(EndpointHitMapper::toDto).collect(Collectors.toList());
    }
}
