package com.postpc.nimrod.sappa_postpc.main.newpost;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

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
import static android.content.Context.MODE_PRIVATE;

class NewPostPresenter implements NewPostContract.Presenter{

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

    NewPostPresenter(NewPostContract.View view, Preferences prefs, LocationUtils locationUtils) {
        this.view = view;
        this.prefs = prefs;
        this.locationUtils = locationUtils;
    }

    @Override
    public void init() {
        initFirebaseDatabase();
        view.initCategoryLayout(categoryExpandedState);
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


    private void initFirebaseDatabase() {
        mStorageRef = FirebaseStorage.getInstance().getReference(); // Initialize reference to Storage.
        database = FirebaseDatabase.getInstance();  // Retrieve an instance of the DB.
        myRef = database.getReference("posts"); // Reference the 'posts'.
    }
}
