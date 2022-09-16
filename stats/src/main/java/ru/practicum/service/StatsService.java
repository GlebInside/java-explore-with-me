package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.HitsCount;
import ru.practicum.StatsMapper;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
public class StatsService {

    private final StatsRepository statsRepository;

    public void saveHit(EndpointHit dto) {
        statsRepository.saveAndFlush(StatsMapper.fromDto(dto));
    }

    public Collection<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        return Arrays.stream(uris)
                .flatMap(
                        uri -> processUri(start, end, uri))
                .collect(Collectors.toList());
    }

    private Stream<ViewStats> processUri(LocalDateTime start, LocalDateTime end, String uri) {
        var hitsCounts = statsRepository.getHits(start, end, uri);
        if (hitsCounts.size() == 0) {
            var hitsCount = new HitsCount() {
                @Override
                public int getHitsCount() {
                    return 0;
                }

                @Override
                public String getApp() {
                    return null;
                }

                @Override
                public String getUri() {
                    return uri;
                }
            };
            hitsCounts = List.of(hitsCount);
        }
        return hitsCounts.stream().map(StatsMapper::toViewStats);
    }
}
