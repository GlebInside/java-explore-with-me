package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.HitsCount;
import ru.practicum.StatsMapper;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class StatsService {

    private final StatsRepository statsRepository;
    public void saveHit(EndpointHit dto) {
        statsRepository.saveAndFlush(StatsMapper.fromDto(dto));
    }

    public Collection<ViewStats> getStats(int start, int end, String[] uris, boolean unique) {
        return Arrays.stream(uris)
                .map(uri->processUri(start, end, uri))
//                .map(x->StatsMapper.toViewStats(x, uri))
                .collect(Collectors.toList());
    }

    private ViewStats processUri(int start, int end, String uri) {
        var hitsCount = statsRepository.getHits(start, end, uri);
        if(hitsCount == null) {
            hitsCount = new HitsCount() {
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
        }
        return StatsMapper.toViewStats(hitsCount);
    }
}
