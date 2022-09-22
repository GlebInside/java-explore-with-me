package ru.practicum.admin.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.users.UserMapper;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.model.User;
import ru.practicum.admin.users.storage.AdminUserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminUserRepository repository;

    @Transactional
    public UserDto addNew(UserDto dto) {
        var model = UserMapper.createFromDto(dto);
        var added = repository.saveAndFlush(model);
        return UserMapper.fromModel(added);
    }

    @Transactional
    public UserDto update(UserDto dto) {
        var model = repository.getReferenceById(dto.getId());
        UserMapper.updateFromDto(dto, model);
        return UserMapper.fromModel(model);
    }

    @Transactional
    public void delete(int id) {
        var model = repository.getReferenceById(id);
        repository.delete(model);
    }

    public Collection<UserDto> get(Integer[] ids, int from, Integer size) {
        var users =  repository.findAllById(Arrays.asList(ids), PageRequest.of(from, size)).stream().map(UserMapper::fromModel).collect(Collectors.toList());
        return users;
    }

    public User getById(int userId) {
        return repository.getReferenceById(userId);
    }
}
