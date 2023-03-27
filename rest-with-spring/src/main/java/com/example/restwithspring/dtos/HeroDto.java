package com.example.restwithspring.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class HeroDto extends RepresentationModel<HeroDto> implements Serializable {
    private int Id;
    @NotBlank
    @NotNull
    @Size(max = 200)
    private String name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
