package ru.practicum.admin.users;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.dto.UserShortDto;
import ru.practicum.admin.users.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User createFromDto(UserDto dto) {
        var model = new User();
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        return model;
    }

    public static User updateFromDto(UserDto dto, User model) {
        if (dto.getName() != null) {
            model.setName(dto.getName());
        }
        if (dto.getName() != null) {
            model.setEmail(dto.getEmail());
        }
        return model;
    }

    public static UserDto fromModel(User model) {
        return new UserDto(model.getId(), model.getEmail(), model.getName());
    }

    public static UserShortDto shortDtoFromModel(User model) {
        return new UserShortDto(model.getId(), model.getName());
    }
}
