package ru.practicum.priv.requests.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.admin.events.service.AdminEventService;
import ru.practicum.admin.users.service.UserService;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.priv.requests.RequestMapper;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.model.Request;
import ru.practicum.priv.requests.model.Status;
import ru.practicum.priv.requests.storage.RequestRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final AdminEventService adminEventService;
    private final UserService userService;

    public Collection<ParticipationRequestDto> getAllRequests(int requesterId) {
        return requestRepository.findByRequesterId(requesterId).stream().map(RequestMapper::fromModel).collect(Collectors.toList());
    }

    public ParticipationRequestDto addNew(int eventId, int userId) {
        var model = new Request();
        model.setEvent(adminEventService.getById(eventId));
        model.setRequester(userService.getById(userId));
        return RequestMapper.fromModel(requestRepository.saveAndFlush(model));
    }

    public ParticipationRequestDto cancel(int requestId, int userId) {
        var model = requestRepository.getReferenceById(requestId);
        if(model.getRequester().getId() != userId) {
            throw new NotFoundException("request id doesn't belong to this user");
        }
        model.setStatus(Status.CANCELED);
        return RequestMapper.fromModel(requestRepository.saveAndFlush(model));
    }

    public Collection<ParticipationRequestDto> getAllRequests(int initiatorId, int eventId) {
        var model = adminEventService.getById(eventId);
        if(model.getInitiator().getId() != initiatorId) {
            throw new BadRequestException("this is not your event");
        }
        return requestRepository.findByEventId(eventId).stream().map(RequestMapper::fromModel).collect(Collectors.toList());
    }

    public ParticipationRequestDto confirm(int initiatorId, int eventId, int requestId) {
        var model = requestRepository.getReferenceById(requestId);
        if(model.getEvent().getId() != eventId) {
            throw new BadRequestException("this event doesn't have such request");
        }
        if(model.getEvent().getInitiator().getId() != initiatorId) {
            throw new BadRequestException("wrong initiator");
        }
        model.setStatus(Status.CONFIRMED);
        requestRepository.saveAndFlush(model);
        return RequestMapper.fromModel(model);
    }

    public ParticipationRequestDto reject(int initiatorId, int eventId, int requestId) {
        var model = requestRepository.getReferenceById(requestId);
        if(model.getEvent().getId() != eventId) {
            throw new BadRequestException("this event doesn't have such request");
        }
        if(model.getEvent().getInitiator().getId() != initiatorId) {
            throw new BadRequestException("wrong initiator");
        }
        model.setStatus(Status.REJECTED);
        requestRepository.saveAndFlush(model);
        return RequestMapper.fromModel(model);
    }
}
