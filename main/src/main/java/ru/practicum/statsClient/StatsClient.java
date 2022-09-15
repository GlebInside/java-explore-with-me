package ru.practicum.statsClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

//    private static final String API_PREFIX = "/stats";

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
        var e = get("/stats", null, Map.of("start", URLEncoder.encode(start.toString()), "end", end, "uris", uri));
        var json = e.getBody();
        var viewStats = objectMapper.readValue((String) json, ViewStats[].class);
        return viewStats[0];
    }
}
