package ru.practicum.admin.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    @Email
    @NotBlank(groups = Create.class)
    private String email;
    @NotBlank(groups = Create.class)
    private String name;
}
