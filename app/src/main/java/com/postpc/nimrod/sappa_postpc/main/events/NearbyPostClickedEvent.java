package com.postpc.nimrod.sappa_postpc.main.events;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

public class NearbyPostClickedEvent {

    private NearbyPostModel nearbyPostModel;

    public NearbyPostClickedEvent(NearbyPostModel nearbyPostModel) {
        this.nearbyPostModel = nearbyPostModel;
    }

    public NearbyPostModel getNearbyPostModel() {
        return nearbyPostModel;
    }
}
