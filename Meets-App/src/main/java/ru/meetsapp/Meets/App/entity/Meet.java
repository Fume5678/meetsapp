package ru.meetsapp.Meets.App.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "meet")
public class Meet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;
    @ElementCollection
    @CollectionTable(name="friend_list", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="friends")
    private List<Integer> meetUser = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "meet", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Column(nullable = false, columnDefinition = "BIT default 0")
    private boolean isPublic;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
