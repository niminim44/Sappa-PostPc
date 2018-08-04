package com.postpc.nimrod.sappa_postpc.main.nearbypost;

import android.os.Bundle;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.PostModel;

import java.util.ArrayList;
import java.util.List;

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
    private PostModel nearbyPostModel;

    NearbyPostPresenter(NearbyPostContract.View view, UiUtils uiUtils) {
        this.view = view;
        this.uiUtils = uiUtils;
    }

    @Override
    public void init() {
        Bundle args = view.getPostArguments();
        nearbyPostModel = toNearbyPostModel(args);
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

    @Override
    public void onNeedItClicked() {
        String[] pictureDialogItems = getDialogItems();
        view.createAlertDialog(R.string.select_action, pictureDialogItems, (dialog, which) -> {
            switch (which) {
                case 0:
                    view.callOwner(nearbyPostModel.getPhone());
                    break;
                case 1:
                    view.messageOwner(nearbyPostModel.getTitle(), nearbyPostModel.getPhone());
                    break;
                case 2:
                    view.whatsappOwner(nearbyPostModel.getTitle(), nearbyPostModel.getPhone());
                    break;
                case 3:
                    view.emailOwner(nearbyPostModel.getTitle(), nearbyPostModel.getEmail());
                    break;
            }
        });
    }

    private String[] getDialogItems() {
        List<String> itemsList = new ArrayList<>();
        if(nearbyPostModel.getPhone() != null && !nearbyPostModel.getPhone().equals("")){
            itemsList.add("Call owner");
            itemsList.add("Send SMS message");
            itemsList.add("Send WhatsApp message");
        }
        if(nearbyPostModel.getEmail() != null && !nearbyPostModel.getEmail().equals("")){
            itemsList.add("Send Email");
        }
        String[] items = new String[itemsList.size()];
        for(int i = 0; i < itemsList.size(); i++){
            items[i] = itemsList.get(i);
        }
        return items;
    }
}
