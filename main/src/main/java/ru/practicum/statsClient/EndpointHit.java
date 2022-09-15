package ru.practicum.statsClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    String app;
    String uri;
    String ip;
    int timestamp;
}
