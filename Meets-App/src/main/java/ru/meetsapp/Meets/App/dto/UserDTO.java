package ru.meetsapp.Meets.App.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDTO {
    public String name;
    public String lastname;
    @NotEmpty
    public String username;
    public String password;
    @Email
    public String email;
    @NotEmpty
    public Long userId;
}
