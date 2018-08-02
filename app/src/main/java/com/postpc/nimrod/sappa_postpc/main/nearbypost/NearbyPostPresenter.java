package com.postpc.nimrod.sappa_postpc.main.nearbypost;

import android.os.Bundle;

import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.PostModel;

class NearbyPostPresenter implements NearbyPostContract.Presenter{

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String IMAGE_URL = "image_url";
    static final String CATEGORY = "category";
    static final String POST_ID = "post id";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
    static final String USER_ID = "user id";
    static final String USER_NAME = "user name";
    static final String PHONE = "phone";
    static final String EMAIL= "email";

    private NearbyPostContract.View view;
    private UiUtils uiUtils;

    NearbyPostPresenter(NearbyPostContract.View view, UiUtils uiUtils) {
        this.view = view;
        this.uiUtils = uiUtils;
    }

    @Override
    public void init() {
        Bundle args = view.getPostArguments();
        PostModel nearbyPostModel = toNearbyPostModel(args);
        view.setImage(nearbyPostModel.getImageUrl(),
                (int)uiUtils.convertPixelsToDp(view.getScreenWidth()), 250);
        view.setTitle(nearbyPostModel.getTitle());
        view.setDistance(nearbyPostModel.getDistance());
        view.setDescription(nearbyPostModel.getDescription());
        view.setCategory(nearbyPostModel.getCategory());
    }

    private PostModel toNearbyPostModel(Bundle args) {
        return new PostModel(
                args.getString(POST_ID),
                args.getString(IMAGE_URL),
                args.getString(TITLE),
                args.getString(DESCRIPTION),
                args.getDouble(LATITUDE),
                args.getDouble(LONGITUDE),
                args.getString(USER_ID),
                args.getString(USER_NAME),
                args.getString(PHONE),
                args.getString(CATEGORY),
                args.getString(EMAIL)
        );
    }

    @Override
    public void backButtonClicked() {
        view.callOnBackPressed();
    }
}
