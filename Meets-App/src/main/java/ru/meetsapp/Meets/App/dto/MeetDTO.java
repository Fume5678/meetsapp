package ru.meetsapp.Meets.App.dto;

import ru.meetsapp.Meets.App.entity.User;

import java.time.LocalDateTime;

public class MeetDTO {
    public String title;
    public String location;
    public User creator;
    public String meetDate;
    public String meetTime;
    public boolean open;
}
