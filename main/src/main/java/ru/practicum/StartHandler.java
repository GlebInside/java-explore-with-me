package ru.practicum;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.practicum.admin.categories.dto.CategoryDto;
import ru.practicum.admin.categories.service.CategoryService;
import ru.practicum.admin.events.dto.Location;
import ru.practicum.admin.events.dto.NewEventDto;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.service.UserService;
import ru.practicum.admin.users.storage.UserRepository;
import ru.practicum.priv.events.service.UserEventService;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class StartHandler {
    private final UserService userService;
    private final CategoryService categoryService;
    private final UserEventService userEventService;
    private final UserRepository userRepository;
    @PostConstruct
    public void postConstruct() {
       var userDto = userService.addNew(new UserDto(5, "user1", "email1"));
        System.out.println(userDto);
        var users = userRepository.findAll();
        var user = userRepository.getReferenceById(1);
        user = userService.getById(userDto.getId());
//        System.out.println(user);
        var category = categoryService.addNew(new CategoryDto());
        System.out.println(category);
        var event = new NewEventDto();
        event.setLocation(new Location(1, 1));
        event.setCategory(category.getId());
//        userEventService.addNew(userDto.getId(), event);
    }
}
