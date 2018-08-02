package com.postpc.nimrod.sappa_postpc.models;


/**
 * This might be the only PostModel we need, so we wont have to convert to NearbyPostModel
 * or MyPostModel each time. Essentially it holds all the fields we usually need.
 */
public class NewPostModel {
    private String imageUrl;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String userID;
    private String contact;
    private String phone;
    private String category;
    private String email;
    //TODO - add category field (String or CONSTANT int) so we can filter posts when retrieving nearby posts.

    // Empty constructor for Firebase to retrieve data.
    public NewPostModel() {}

    // Main constructor.
    public NewPostModel(String imageUrl, String title, String description, double latitude, double longitude, String userID, String contact, String phone, String category, String email) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
        this.contact = contact;
        this.phone = phone;
        this.category = category;
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    // Returns img URL of the stored file in Firebase Storage.
    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    //    public String getLocation() {
//        return location;
//    }

//    public String getDistance() {
//        return distance;
//    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getUserID() {
        return userID;
    }

    public String getContact() {
        return contact;
    }

    public String getPhone() {
        return phone;
    }

}
