package ru.practicum.priv.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventShortDto;
import ru.practicum.priv.events.service.UserEventService;
import ru.practicum.priv.events.storage.UserEventRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserEventService userEventService;

    @Transactional
    public void subscribe(int subscriberId, int userId) {
        var model = userRepository.getReferenceById(userId);
        var subscriber = userRepository.getReferenceById(subscriberId);
        model.getSubscribers().add(subscriber);
        userRepository.saveAndFlush(model);
    }

    public Collection<EventShortDto> getSubscriptionCreatedEvents(int subscriberId) {
        var users = userRepository.findBySubscribersId(subscriberId);

        return users.stream().flatMap(u -> userEventService.getActualEvents(u).stream()).collect(Collectors.toList());
    }
}
