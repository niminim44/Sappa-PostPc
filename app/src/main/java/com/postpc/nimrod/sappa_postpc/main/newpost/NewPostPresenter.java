package com.postpc.nimrod.sappa_postpc.main.newpost;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationUtils;
import com.postpc.nimrod.sappa_postpc.models.NewPostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import static android.app.Activity.RESULT_OK;

class NewPostPresenter implements NewPostContract.Presenter{

    private static final String DEFAULT_CATEGORY = "default category";
    private static final String ELECTRONICS_CATEGORY = "Electronics";
    private static final String FURNITURE_CATEGORY = "Furniture";
    private static final String BOOKS_CATEGORY = "Books";
    private static final String CLOTHING_CATEGORY = "Clothing";
    private static final String SPORTS_CATEGORY = "Sports";
    private static final String CHILDREN_CATEGORY = "Children";
    private static final String OTHER_CATEGORY = "Other";



    private NewPostContract.View view;
    private Preferences prefs;
    private LocationUtils locationUtils;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private boolean categoryExpandedState = false;
    private double longitude;
    private double latitude;
    private Uri imageUri;
    private String selectedRadioButton;

    NewPostPresenter(NewPostContract.View view, Preferences prefs, LocationUtils locationUtils) {
        this.view = view;
        this.prefs = prefs;
        this.locationUtils = locationUtils;
    }

    @Override
    public void init() {
        selectedRadioButton = DEFAULT_CATEGORY;
        initFirebaseDatabase();
        view.initCategoryLayout(categoryExpandedState);
        view.disablePublishButton();
        view.initCategoryRadioGroup();
    }

    @Override
    public void onCategoryPreOpen() {
        categoryExpandedState = true;
    }

    @Override
    public void onCategoryPreClose() {
        categoryExpandedState = false;
    }

    @Override
    public void onPublishClicked() {
        // Get additional info for post.
        String userId = prefs.getUserId();
        locationUtils.getDeviceLocation(location -> {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        });

        // Use push() to create a post unique key in the node containing posts.
        String key = myRef.push().getKey();

        // Upload photo to storage using a Task.
        final StorageReference ref = mStorageRef.child("images/" + key +".jpg");    // Set image name for storage.
        UploadTask uploadTask = ref.putFile(imageUri);

        //TODO - use irlTask to display progress bar somewhere.
        uploadTask.continueWithTask(task -> getDownloadUrl(ref, task))
                .addOnCompleteListener(task -> saveNewPost(userId, key, task));
    }

    private void saveNewPost(String userId, String key, Task<Uri> task) {
        if (task.isSuccessful()) {
            Uri downloadUri = task.getResult();
            view.showToast(R.string.image_uploaded_successfully);
            // Add post to DB.
            NewPostModel newPost = new NewPostModel(downloadUri.toString(),
                    view.getTitle(),
                    view.getDescription(),
                    latitude,
                    longitude,
                    userId,
                    view.getContactName(),
                    Long.parseLong(view.getContactPhone()), view.getCategory());

            // Write a message to the database.
            myRef.child(key).setValue(newPost);
            view.showToast(R.string.post_published_successfully);

        } else {
            view.showToast(R.string.failed_uploading_new_post);
        }
    }

    @NonNull
    private Task<Uri> getDownloadUrl(StorageReference ref, Task<UploadTask.TaskSnapshot> task) {
        if (!task.isSuccessful()) {
            view.showToast(R.string.failed_to_upload_image);
        }

        // Continue with the task to get the download URL
        return ref.getDownloadUrl();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageUri = selectedImage;
                    view.setImageUri(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageUri = selectedImage;
                    view.setImageUri(selectedImage);
                }
                break;
        }
    }

    @Override
    public void onCategorySelected(int categoryId) {
        switch (categoryId){
            case R.id.electronics_checkbox:
                selectedRadioButton = ELECTRONICS_CATEGORY;
                view.setCategoryTitle(ELECTRONICS_CATEGORY);
                break;
            case R.id.furniture_checkbox:
                selectedRadioButton = FURNITURE_CATEGORY;
                view.setCategoryTitle(FURNITURE_CATEGORY);
                break;
            case R.id.books_checkbox:
                selectedRadioButton = BOOKS_CATEGORY;
                view.setCategoryTitle(BOOKS_CATEGORY);
                break;
            case R.id.clothing_checkbox:
                selectedRadioButton = CLOTHING_CATEGORY;
                view.setCategoryTitle(CLOTHING_CATEGORY);
                break;
            case R.id.sports_checkbox:
                selectedRadioButton = SPORTS_CATEGORY;
                view.setCategoryTitle(SPORTS_CATEGORY);
                break;
            case R.id.children_checkbox:
                selectedRadioButton = CHILDREN_CATEGORY;
                view.setCategoryTitle(CHILDREN_CATEGORY);
                break;
            case R.id.other_checkbox:
                selectedRadioButton = OTHER_CATEGORY;
                view.setCategoryTitle(OTHER_CATEGORY);
                break;

        }
    }

    private void initFirebaseDatabase() {
        mStorageRef = FirebaseStorage.getInstance().getReference(); // Initialize reference to Storage.
        database = FirebaseDatabase.getInstance();  // Retrieve an instance of the DB.
        myRef = database.getReference("posts"); // Reference the 'posts'.
    }
}
