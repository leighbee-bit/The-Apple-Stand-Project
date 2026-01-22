package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "apples")
public class Apple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "appletype")
    private String type;

    @Column(name = "color")
    private String color;

    @Column(name = "weight")
    private int weight;

    public Apple() {}
    public Apple(Long id, String type, String color, int weight) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.weight = weight;
    }


    //Getters
    public int getWeight() {
        return this.weight;
    }
    public String getType() {
        return this.type;
    }
    public String getColor() {
        return this.color;
    }
    public Long getId() {
        return this.id;
    }

    //Setters
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
