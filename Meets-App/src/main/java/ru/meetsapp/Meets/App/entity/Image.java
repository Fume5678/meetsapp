package ru.meetsapp.Meets.App.entity;

import lombok.Data;

@Data
public class Image {
    private long id;
    private String name;
    private byte[] imageBytes;
    private long userId;
}
