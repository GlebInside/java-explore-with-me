package ru.practicum.statsClient;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<String> get(String path) {
        return get(path, null, Map.of());
    }

    protected ResponseEntity<String> get(String path, @Nullable Map<String, Object> parameters, Map<String, Object> queryParameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null, queryParameters);
    }

    protected <T> ResponseEntity<String> post(String path, T body) {
        return post(path, null, body, Map.of());
    }

    protected <T> ResponseEntity<String> post(String path, @Nullable Map<String, Object> parameters, T body, Map<String, Object> queryParameters) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, body, queryParameters);
    }

    private <T> ResponseEntity<String> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> uriParameters, @Nullable T body, @NotNull Map<String, Object> queryParameters) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        var builder = UriComponentsBuilder.fromUriString(path);
        assert queryParameters != null;
        queryParameters.forEach(builder::queryParam);
        var components = builder.build();
        path = components.toUriString();
        ResponseEntity<String> exploreWithMeServerResponse;
        try {
            if (uriParameters != null) {
                exploreWithMeServerResponse = rest.exchange(path, method, requestEntity, String.class, uriParameters);
            } else {
                exploreWithMeServerResponse = rest.exchange(path, method, requestEntity, String.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
        return prepareGatewayResponse(exploreWithMeServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<String> prepareGatewayResponse(ResponseEntity<String> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
