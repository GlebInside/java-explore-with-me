package ru.practicum.priv.users;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EventShortDto;
import ru.practicum.priv.events.service.UserEventService;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserService {

    private final UserRepository userRepository;
    private final UserEventService userEventService;

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
