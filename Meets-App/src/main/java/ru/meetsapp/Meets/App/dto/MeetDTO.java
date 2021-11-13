package ru.meetsapp.Meets.App.dto;

import ru.meetsapp.Meets.App.entity.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class MeetDTO {
    @NotEmpty
    public String title;
    @NotEmpty
    public String location;
    @NotEmpty
    public User creator;
    @NotEmpty
    public String meetDate;
    @NotEmpty
    public String meetTime;
    public boolean open = false;
}
