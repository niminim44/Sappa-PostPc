package com.postpc.nimrod.sappa_postpc.main.mypost;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.PostModel;

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

    private MyPostContract.View view;
    private UiUtils uiUtils;

    MyPostPresenter(MyPostContract.View view, UiUtils uiUtils) {
        this.view = view;
        this.uiUtils = uiUtils;
    }


    @Override
    public void init() {
        Bundle args = view.getPostArguments();
        PostModel nearbyPostModel = toMyPostModel(args);
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

    }

    @Override
    public void onDeleteClicked() {

        // Delete file.
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(IMAGE_URL);
        storageRef.delete().addOnSuccessListener(aVoid ->  {
            // File deleted successfully

            // Get a reference to our posts.
            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("posts");
            dbReference.child("key").removeValue();  //TODO - we need the post key + show toast + refresh myPosts fragment

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
                args.getString(EMAIL)
        );
    }
}
