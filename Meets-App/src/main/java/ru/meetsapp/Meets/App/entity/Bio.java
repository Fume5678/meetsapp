package ru.meetsapp.Meets.App.entity;

import lombok.Data;

@Data
public class Bio {
    private long Id;
    private float height;
    private float weight;
    private String hairColor;
    private String gender;
    private String biography;
    private String work;
    private String specialSigns;
    private User user;

}
