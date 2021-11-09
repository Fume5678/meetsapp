package ru.meetsapp.Meets.App.entity;

import lombok.Data;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Data
public class Comment {
    private long id;
    private long userId;
    private Meet meet;
    private String text;
    private String message;
    private String username;

    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
