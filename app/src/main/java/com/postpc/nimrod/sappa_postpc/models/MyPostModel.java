package com.postpc.nimrod.sappa_postpc.models;

public class MyPostModel {

    private String imageUrl;
    private String title;
    private String description;
    private String category;

    public MyPostModel(String imageUrl, String title, String description, String category) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
