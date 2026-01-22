package com.example.javamachinery;

/**
 * This is the Android representation of the Apple in the PostgreSQL database. This looks exactly
 * like the Apple model in the Spring Boot API. It has the same attributes, getters and setters.
 **/


public class Apple {
    private Long id;
    private String color;
    private String type;
    private int weight;

    public Apple(String color, String type, int weight) {
        this.color = color;
        this.type = type;
        this.weight = weight;
    }

    //Matching getters with API
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

    //Matching setters with API
    public void setWeight(int weight) { this.weight = weight; }
    public void setColor(String color) {
        this.color = color;
    }
    public void setType(String type) { this.type = type; }
}
