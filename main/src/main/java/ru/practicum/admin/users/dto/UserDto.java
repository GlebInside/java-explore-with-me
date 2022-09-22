package ru.practicum.admin.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;
import ru.practicum.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(groups = Update.class)
    private int id;
    @Email
    @NotBlank(groups = {Create.class, Update.class})
    private String email;
    @NotBlank(groups = Create.class)
    private String name;
}
