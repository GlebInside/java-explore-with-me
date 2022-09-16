package ru.practicum.admin.users.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.admin.users.UserMapper;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.model.User;
import ru.practicum.admin.users.storage.UserRepository;
import ru.practicum.exception.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserService {

    private final UserRepository repository;

    public UserDto addNew(UserDto dto) {
        var model = UserMapper.createFromDto(dto);
        var added = repository.saveAndFlush(model);
        return UserMapper.fromModel(added);
    }

    public UserDto update(UserDto dto) {
        var model = repository.getReferenceById(dto.getId());
        UserMapper.updateFromDto(dto, model);
        return UserMapper.fromModel(repository.saveAndFlush(model));
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("missing user with id " + id);
        }
        var model = repository.getReferenceById(id);
        repository.delete(model);
    }

    public Collection<UserDto> get(int from, Integer size) {
        return repository.findAll(PageRequest.of(from, size)).stream().map(UserMapper::fromModel).collect(Collectors.toList());
    }

    public User getById(int userId) {
        return repository.getReferenceById(userId);
    }
}
