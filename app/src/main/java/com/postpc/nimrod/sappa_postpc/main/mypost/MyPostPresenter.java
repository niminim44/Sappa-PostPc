package com.postpc.nimrod.sappa_postpc.main.mypost;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.postpc.nimrod.sappa_postpc.main.events.EditPostEvent;
import com.postpc.nimrod.sappa_postpc.main.events.RefreshDataEvent;
import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.PostModel;

import org.greenrobot.eventbus.EventBus;

public class MyPostPresenter implements MyPostContract.Presenter{

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
    static final String TIMESTAMP = "timestamp";
    private final EventBus eventBus;

    private MyPostContract.View view;
    private UiUtils uiUtils;
    private PostModel nearbyPostModel;

    MyPostPresenter(MyPostContract.View view, UiUtils uiUtils, EventBus eventBus) {
        this.view = view;
        this.uiUtils = uiUtils;
        this.eventBus = eventBus;
    }


    @Override
    public void init() {
        Bundle args = view.getPostArguments();
        nearbyPostModel = toMyPostModel(args);
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

    @Override
    public void onEditClicked() {
        view.callOnBackPressed();
        eventBus.post(new EditPostEvent(new PostModel(nearbyPostModel)));
    }

    @Override
    public void onDeleteClicked() {
        view.showDeleteProgressBar();
        // Delete file.
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(nearbyPostModel.getImageUrl());
        storageRef.delete().addOnSuccessListener(aVoid ->  {
            // File deleted successfully

            // Get a reference to our posts.
            DatabaseReference dbReference = FirebaseDatabase.getInstance()
                    .getReference("posts");
            dbReference.child(nearbyPostModel.getPostId())
                    .removeValue();
            eventBus.post(new RefreshDataEvent());
            view.callOnBackPressed();
        }).addOnFailureListener(exception -> {
            // Uh-oh, an error occurred!

        });

    }

    private PostModel toMyPostModel(Bundle args) {
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
                args.getString(EMAIL),
                args.getString(TIMESTAMP));
    }
}
