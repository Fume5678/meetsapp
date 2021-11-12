package ru.meetsapp.Meets.App.dto;

import ru.meetsapp.Meets.App.entity.Meet;
import ru.meetsapp.Meets.App.entity.User;

public class CommentDTO {
    public Meet meet;
    public Long userId;
    public String username;
    public String message;
}
