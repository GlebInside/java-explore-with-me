package ru.practicum.priv.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.events.storage.AdminEventRepository;
import ru.practicum.admin.users.storage.AdminUserRepository;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.priv.requests.RequestMapper;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.model.Request;
import ru.practicum.priv.requests.model.Status;
import ru.practicum.priv.requests.storage.RequestRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;
    private final AdminEventRepository adminEventRepository;
    private final AdminUserRepository adminUserRepository;

    public Collection<ParticipationRequestDto> getAllRequests(int requesterId) {
        return requestRepository.findByRequesterId(requesterId).stream().map(RequestMapper::fromModel).collect(Collectors.toList());
    }

    @Transactional
    public ParticipationRequestDto addNew(int eventId, int userId) {
        var model = new Request();
        model.setEvent(adminEventRepository.getReferenceById(eventId));
        model.setRequester(adminUserRepository.getReferenceById(userId));
        return RequestMapper.fromModel(requestRepository.saveAndFlush(model));
    }

    @Transactional
    public ParticipationRequestDto cancel(int requestId, int userId) {
        var model = requestRepository.getReferenceById(requestId);
        if (model.getRequester().getId() != userId) {
            throw new NotFoundException("request id doesn't belong to this user");
        }
        model.setStatus(Status.CANCELED);
        return RequestMapper.fromModel(requestRepository.saveAndFlush(model));
    }

    public Collection<ParticipationRequestDto> getAllRequests(int initiatorId, int eventId) {
        var model = adminEventRepository.getReferenceById(eventId);
        if (model.getInitiator().getId() != initiatorId) {
            throw new BadRequestException("this is not your event");
        }
        return requestRepository.findByEventId(eventId).stream().map(RequestMapper::fromModel).collect(Collectors.toList());
    }

    @Transactional
    public ParticipationRequestDto confirm(int initiatorId, int eventId, int requestId) {
        var model = requestRepository.getReferenceById(requestId);
        if (model.getEvent().getId() != eventId) {
            throw new BadRequestException("this event doesn't have such request");
        }
        if (model.getEvent().getInitiator().getId() != initiatorId) {
            throw new BadRequestException("wrong initiator");
        }
        model.setStatus(Status.CONFIRMED);
        requestRepository.saveAndFlush(model);
        return RequestMapper.fromModel(model);
    }

    @Transactional
    public ParticipationRequestDto reject(int initiatorId, int eventId, int requestId) {
        var model = requestRepository.getReferenceById(requestId);
        if (model.getEvent().getId() != eventId) {
            throw new BadRequestException("this event doesn't have such request");
        }
        if (model.getEvent().getInitiator().getId() != initiatorId) {
            throw new BadRequestException("wrong initiator");
        }
        model.setStatus(Status.REJECTED);
        requestRepository.saveAndFlush(model);
        return RequestMapper.fromModel(model);
    }
}
