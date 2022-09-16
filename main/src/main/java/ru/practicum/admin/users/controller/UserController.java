package ru.practicum.admin.users.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.service.UserService;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addNew(@RequestBody UserDto dto) {
        try {
            return userService.addNew(dto);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PatchMapping
    public UserDto update(@RequestBody UserDto dto) {
        try {
            return userService.update(dto);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping
    public Collection<UserDto> get(@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "100") Integer size) {
        return userService.get(from, size);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }
}
