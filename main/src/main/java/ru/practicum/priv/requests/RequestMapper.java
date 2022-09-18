package ru.practicum.priv.requests;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.model.Request;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto fromModel(Request model) {
        var dto = new ParticipationRequestDto();
        dto.setRequester(model.getRequester().getId());
        dto.setCreated(model.getCreated());
        dto.setEventId(model.getEvent().getId());
        dto.setStatus(model.getStatus());
        dto.setId(model.getId());
        return dto;
    }
}
