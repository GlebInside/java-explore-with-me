package ru.practicum.statsClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {


    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
        System.out.println("url = " + serverUrl);
    }

    public void saveHit(String ip, String uri) {
        var dto = new EndpointHit();
        dto.setApp("explore-with-me");
        dto.setIp(ip);
        dto.setTimestamp(LocalDateTime.now());
        dto.setUri(uri);
        post("/hit", dto);
    }

    public ViewStats getStats(LocalDateTime start, LocalDateTime end, String uri) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedEnd = end.format(formatter);
        String formattedStart = start.format(formatter);
        var e = get("/stats", null, Map.of("start", formattedStart, "end", formattedEnd, "uris", uri));
        var json = e.getBody();
        var viewStats = objectMapper.readValue(json, ViewStats[].class);
        return viewStats[0];
    }
}
