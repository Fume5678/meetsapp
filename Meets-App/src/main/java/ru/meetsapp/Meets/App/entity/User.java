package ru.meetsapp.Meets.App.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.meetsapp.Meets.App.entity.enums.ERole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {
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
    @Column(columnDefinition = "INTEGER default 0")
    private int likes;

    @Column
    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    private Set<Long> likedUsers = new HashSet<>();
    @Column
    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    private Set<Long> bookmarkUsers = new HashSet<>();
    @Column
    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    private Set<Long> friends = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Bio bio;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

