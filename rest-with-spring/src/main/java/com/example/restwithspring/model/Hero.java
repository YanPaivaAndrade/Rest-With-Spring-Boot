package com.example.restwithspring.model;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "TB_HERO")
public class Hero implements Serializable {
    private static final  long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 200)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}