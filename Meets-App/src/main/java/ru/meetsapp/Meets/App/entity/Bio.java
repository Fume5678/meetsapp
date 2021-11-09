package ru.meetsapp.Meets.App.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bio")
public class Bio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float height;
    private float weight;
    private String hairColor;
    private String gender;
    @Column(nullable = true, columnDefinition = "Text")
    private String biography;
    private String work;
    private String specialSigns;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

}
