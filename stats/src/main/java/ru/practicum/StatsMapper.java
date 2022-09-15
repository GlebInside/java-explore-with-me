package ru.practicum;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.Stats;

import java.util.Collection;

public class StatsMapper {

    public static Stats fromDto(EndpointHit dto) {
        var stats = new Stats();
        stats.setTimestamp(dto.getTimestamp());
        stats.setUri(dto.getUri());
        stats.setIp(dto.getIp());
        stats.setApp(dto.getApp());
        return stats;
    }

    public static ViewStats toViewStats(HitsCount hitsCount) {
        var dto = new ViewStats();
        dto.setApp(hitsCount.getApp());
        dto.setHits(hitsCount.getHitsCount());
        dto.setUri(hitsCount.getUri());
        return dto;
    }
}
