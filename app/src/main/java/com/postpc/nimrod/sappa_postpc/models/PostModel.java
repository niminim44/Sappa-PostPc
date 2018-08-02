package com.postpc.nimrod.sappa_postpc.models;

public class PostModel {

    private String postId;
    private String imageUrl;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String userID;
    private String userName;
    private String phone;
    private String category;
    private String email;

    private String distance;

    public PostModel() {
    }

    public PostModel(String postId, String imageUrl, String title, String description, double latitude, double longitude, String userID, String userName, String phone, String category, String email) {
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
        this.userName = userName;
        this.phone = phone;
        this.category = category;
        this.email = email;
    }

    public String getPostId() {
        return postId;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getCategory() {
        return category;
    }

    public String getEmail() {
        return email;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
