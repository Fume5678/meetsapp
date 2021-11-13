package ru.meetsapp.Meets.App.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Optional;

@Getter
@Setter
public class BioDTO {
    public Float height;
    public Float weight;
    public String  hairColor;
    public String  gender;
    public String  biography;
    public String  job;
    public String  specialSigns;
}
