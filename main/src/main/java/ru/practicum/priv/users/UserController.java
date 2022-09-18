package ru.practicum.priv.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.events.dto.EventShortDto;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/{subscriberId}/subscribe/{userId}")
    public void subscribe(@PathVariable int userId, @PathVariable int subscriberId) {
        userService.subscribe(subscriberId, userId);
    }

    @GetMapping("/{subscriberId}/subscriptions/createdEvents")
    public Collection<EventShortDto> getSubscriptionCreatedEvents(@PathVariable int subscriberId) {
        return userService.getSubscriptionCreatedEvents(subscriberId);
    }
}
