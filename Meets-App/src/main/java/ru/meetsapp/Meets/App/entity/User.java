package ru.meetsapp.Meets.App.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;
import ru.meetsapp.Meets.App.entity.enums.ERole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, length = 3000)
    private String password;

    private int likes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Bio bio;
    @ElementCollection
    @CollectionTable(name="friend_list", joinColumns=@JoinColumn(name="user_id"))
    private List<Integer> friends = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator", orphanRemoval = true)
    private List<Meet> userMeets = new ArrayList<>();
    @Column(nullable = false)
    private ERole role;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }

}

