package ru.practicum.priv.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.events.EventMapper;
import ru.practicum.admin.events.model.State;
import ru.practicum.admin.users.model.User;
import ru.practicum.dto.EventShortDto;
import ru.practicum.priv.events.storage.UserEventRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserEventRepository userEventRepository;

    @Transactional
    public void subscribe(int subscriberId, int userId) {
        var model = userRepository.getReferenceById(userId);
        var subscriber = userRepository.getReferenceById(subscriberId);
        model.getSubscribers().add(subscriber);
        userRepository.saveAndFlush(model);
    }

    public Collection<EventShortDto> getSubscriptionCreatedEvents(int subscriberId) {
        var users = userRepository.findBySubscribersId(subscriberId);

        return users.stream().flatMap(u -> getActualEvents(u).stream()).collect(Collectors.toList());
    }

    private Collection<EventShortDto> getActualEvents(User initiator) {
        return userEventRepository.findByInitiator(initiator, null).stream()
                .filter(e -> e.getState() == State.PUBLISHED)
                .filter(e -> e.getEventDate().isAfter(LocalDateTime.now()))
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }
}
