package ru.meetsapp.Meets.App.dto;

import lombok.Getter;
import lombok.Setter;
import ru.meetsapp.Meets.App.entity.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class MeetDTO {
    @NotEmpty
    public String title;
    @NotEmpty
    public String location;
    @NotEmpty
    public String creator;
    @NotEmpty
    public String meetDate;
    @NotEmpty
    public String meetTime;
    public boolean open = false;
}
