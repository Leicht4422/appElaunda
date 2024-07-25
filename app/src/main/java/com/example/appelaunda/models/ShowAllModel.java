package com.example.appelaunda.models;

public class ShowAllModel {

    String description;
    String name;
    String rating;
    int price;
    private String id;
    String img_url;
    String type;

    public ShowAllModel() {
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getType() {
        return type;
    }

    public ShowAllModel(String description, String name, String rating, int price, String id, String img_url, String type) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.id = id;
        this.img_url = img_url;
        this.type = type;
    }
}
