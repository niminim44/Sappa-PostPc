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

    //need to use Long.valueOf(timestamp)
    private String timestamp;

    //this field is not initialized via constructor meaning it can be null
    private float distanceValue;

    public PostModel() {
    }

    public PostModel(String postId, String imageUrl, String title, String description, double latitude, double longitude, String userID, String userName, String phone, String category, String email, String timestamp) {
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
        this.timestamp = timestamp;
    }

    public PostModel(PostModel nearbyPostModel) {
        this.postId = nearbyPostModel.getPostId();
        this.imageUrl = nearbyPostModel.getImageUrl();
        this.title = nearbyPostModel.getTitle();
        this.description = nearbyPostModel.getDescription();
        this.latitude = nearbyPostModel.getLatitude();
        this.longitude = nearbyPostModel.getLongitude();
        this.userID = nearbyPostModel.getUserID();
        this.userName = nearbyPostModel.getUserName();
        this.phone = nearbyPostModel.getPhone();
        this.category = nearbyPostModel.getCategory();
        this.email = nearbyPostModel.getEmail();
        this.timestamp = nearbyPostModel.getTimestamp();
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

    public float getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(float distanceValue) {
        this.distanceValue = distanceValue;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
