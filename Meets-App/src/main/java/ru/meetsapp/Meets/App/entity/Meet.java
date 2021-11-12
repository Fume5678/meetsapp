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
    @Column(columnDefinition = "text", nullable = false)
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime meetDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime meetTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;
    @ElementCollection
    @CollectionTable(name="friend_list", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="friends")
    private List<Long> meetUser = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "meet", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Column(nullable = false, columnDefinition = "INTEGER default 0")
    private int open;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
