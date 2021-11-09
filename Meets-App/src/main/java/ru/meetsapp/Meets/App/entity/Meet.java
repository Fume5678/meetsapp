package ru.meetsapp.Meets.App.entity;

import lombok.Data;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Meet {
    private long id;
    private String title;
    private String location;
    private User creator;
    private List<User> meetUsers = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private boolean isPublic;

    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
