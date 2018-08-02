package com.postpc.nimrod.sappa_postpc.main.events;


import com.postpc.nimrod.sappa_postpc.models.PostModel;

public class NearbyPostClickedEvent {

    private PostModel nearbyPostModel;

    public NearbyPostClickedEvent(PostModel nearbyPostModel) {
        this.nearbyPostModel = nearbyPostModel;
    }

    public PostModel getNearbyPostModel() {
        return nearbyPostModel;
    }
}
