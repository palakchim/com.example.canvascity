package com.example.canvascity;

public class EventModel {

    private String title, date, price, location, description, imageUrl;

    public EventModel() {}

    public EventModel(String title, String date, String price,
                      String location, String description, String imageUrl) {
        this.title = title;
        this.date = date;
        this.price = price;
        this.location = location;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getPrice() { return price; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}