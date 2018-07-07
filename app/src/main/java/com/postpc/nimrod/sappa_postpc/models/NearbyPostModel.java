package com.postpc.nimrod.sappa_postpc.models;

public class NearbyPostModel {

    private String imageUrl;
    private String title;
    private String description;
    private String location;
    private String distance;

    public NearbyPostModel(String imageUrl, String title, String description, String location, String distance) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.location = location;
        this.distance = distance;
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
