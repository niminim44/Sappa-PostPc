package com.postpc.nimrod.sappa_postpc.models;

public class NearbyPostModel {

    private String imageUrl;
    private String title;
    private String description;
    private String location;
    private String distance;
    private String category;

    public NearbyPostModel(String imageUrl, String title, String description, String location, String distance, String category) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.location = location;
        this.distance = distance;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDistance() {
        return distance;
    }
}
