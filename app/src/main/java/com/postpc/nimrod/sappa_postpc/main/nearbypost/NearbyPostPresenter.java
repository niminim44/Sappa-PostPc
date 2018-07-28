package com.postpc.nimrod.sappa_postpc.main.nearbypost;

import android.os.Bundle;

import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

class NearbyPostPresenter implements NearbyPostContract.Presenter{

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String IMAGE_URL = "image_url";
    static final String DISTANCE = "distance";
    static final String LOCATION = "location";
    static final String CATEGORY = "category";

    private NearbyPostContract.View view;
    private UiUtils uiUtils;

    NearbyPostPresenter(NearbyPostContract.View view, UiUtils uiUtils) {
        this.view = view;
        this.uiUtils = uiUtils;
    }

    @Override
    public void init() {
        Bundle args = view.getPostArguments();
        NearbyPostModel nearbyPostModel = toNearbyPostModel(args);
        view.setImage(nearbyPostModel.getImageUrl(),
                (int)uiUtils.convertPixelsToDp(view.getScreenWidth()), 250);
        view.setTitle(nearbyPostModel.getTitle());
        view.setDistance(nearbyPostModel.getDistance());
        view.setDescription(nearbyPostModel.getDescription());
        view.setCategory(nearbyPostModel.getCategory());
    }

    private NearbyPostModel toNearbyPostModel(Bundle args) {
        return new NearbyPostModel(
                args.getString(IMAGE_URL),
                args.getString(TITLE),
                args.getString(DESCRIPTION),
                args.getString(LOCATION),
                args.getString(DISTANCE),
                args.getString(CATEGORY));
    }

    @Override
    public void backButtonClicked() {
        view.callOnBackPressed();
    }
}
