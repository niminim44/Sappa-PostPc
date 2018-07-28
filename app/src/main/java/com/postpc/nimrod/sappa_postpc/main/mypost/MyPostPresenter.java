package com.postpc.nimrod.sappa_postpc.main.mypost;

import android.os.Bundle;

import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.MyPostModel;

public class MyPostPresenter implements MyPostContract.Presenter{

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String IMAGE_URL = "image_url";
    static final String DISTANCE = "distance";
    static final String LOCATION = "location";
    static final String CATEGORY = "category";

    private MyPostContract.View view;
    private UiUtils uiUtils;

    MyPostPresenter(MyPostContract.View view, UiUtils uiUtils) {
        this.view = view;
        this.uiUtils = uiUtils;
    }


    @Override
    public void init() {
        Bundle args = view.getPostArguments();
        MyPostModel nearbyPostModel = toMyPostModel(args);
        String imageUrl = nearbyPostModel.getImageUrl();
        if(imageUrl != null){
            view.setImage(
                    nearbyPostModel.getImageUrl(),
                    (int)uiUtils.convertPixelsToDp(view.getScreenWidth()), 250);
        }
        view.setTitle(nearbyPostModel.getTitle());
        view.setDescription(nearbyPostModel.getDescription());
        view.setCategory(nearbyPostModel.getCategory());
    }

    @Override
    public void backButtonClicked() {
        view.callOnBackPressed();
    }

    private MyPostModel toMyPostModel(Bundle args) {
        return new MyPostModel(
                args.getString(IMAGE_URL),
                args.getString(TITLE),
                args.getString(DESCRIPTION),
                args.getString(CATEGORY)
        );
    }
}
