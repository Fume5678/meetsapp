package ru.meetsapp.Meets.App.entity;

import lombok.Data;
import ru.meetsapp.Meets.App.entity.enums.ERole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
//@Entity(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private int likes;

    private Bio bio = new Bio();
    private Set<Integer> friends = new HashSet<Integer>();
    private ERole role;
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }

}
