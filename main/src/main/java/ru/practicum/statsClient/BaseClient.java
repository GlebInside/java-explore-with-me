package ru.practicum.statsClient;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.net.URLEncoder;
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

//    protected <T> ResponseEntity<Object> put(String path, T body) {
//        return put(path, null, body);
//    }
//
//    protected <T> ResponseEntity<Object> put(String path, @Nullable Map<String, Object> parameters, T body) {
//        return makeAndSendRequest(HttpMethod.PUT, path, parameters, body);
//    }
//
//    protected <T> ResponseEntity<Object> patch(String path, T body) {
//        return patch(path, null, body);
//    }
//
//    protected <T> ResponseEntity<Object> patch(String path) {
//        return patch(path, null, null);
//    }

//    protected <T> ResponseEntity<Object> patch(String path, @Nullable Map<String, Object> parameters, T body) {
//        return makeAndSendRequest(HttpMethod.PATCH, path, parameters, body);
//    }
//
//    protected ResponseEntity<Object> delete(String path) {
//        return delete(path, null);
//    }
//
//    protected ResponseEntity<Object> delete(String path, @Nullable Map<String, Object> parameters) {
//        return makeAndSendRequest(HttpMethod.DELETE, path, parameters, null);
//    }

    private <T> ResponseEntity<String> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> uriParameters, @Nullable T body, @NotNull Map<String, Object> queryParameters) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        var builder = UriComponentsBuilder.fromUriString(path);
        assert queryParameters != null;
        queryParameters.forEach(builder::queryParam);
        path = builder.toUriString();
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
//        if (userId != null) {
//            headers.set("X-Sharer-User-Id", String.valueOf(userId));
//        }
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
