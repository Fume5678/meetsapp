package ru.meetsapp.Meets.App.dto;

import lombok.Getter;
import lombok.Setter;
import ru.meetsapp.Meets.App.entity.Meet;
import ru.meetsapp.Meets.App.entity.User;

@Getter
@Setter
public class CommentDTO {
    public Meet meet;
    public Long userId;
    public String username;
    public String message;
}
