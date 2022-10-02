package ru.practicum.priv.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.priv.events.storage.UserEventRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserEventRepository userEventRepository;

    @Transactional
    public void subscribe(int subscriberId, int userId) {
        var model = userRepository.getReferenceById(userId);
        var subscriber = userRepository.getReferenceById(subscriberId);
        model.getSubscribers().add(subscriber);
    }
}
