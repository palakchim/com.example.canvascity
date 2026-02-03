package com.example.canvascity.model;

public class EventModel {

    private String title;
    private String locationDate;
    private String price;
    private String description;

    // NEW
    private int image;

    // Existing constructor (UNCHANGED)
    public EventModel(String title, String locationDate, String price, String description) {
        this.title = title;
        this.locationDate = locationDate;
        this.price = price;
        this.description = description;
        this.image = 0; // safety default
    }

    // NEW constructor (image support)
    public EventModel(String title, String locationDate, String price,
                      String description, int image) {
        this.title = title;
        this.locationDate = locationDate;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getLocationDate() {
        return locationDate;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
