package com.postpc.nimrod.sappa_postpc.main.newpost;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationProvider;
import com.postpc.nimrod.sappa_postpc.models.NewPostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

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
    private static final Boolean IGNORED_VALUE = true;
    private static final int DESCRIPTION_MAX_LENGTH = 140;
    private final LocationProvider locationProvider;

    private PublishSubject<Boolean> fieldChangedPublishSubject = PublishSubject.create();



    private NewPostContract.View view;
    private Preferences prefs;
    private StorageReference mStorageRef;
    private DatabaseReference myRef;

    private boolean categoryExpandedState = false;
    private Uri imageUri;
    private String selectedRadioButton;
    private boolean emptyTitle = true;
    private boolean emptyDescription = true;
    private boolean validEmail = false;
    private boolean validPhone = false;
    private Location currentLocation;

    NewPostPresenter(NewPostContract.View view, Preferences prefs, LocationProvider locationProvider) {
        this.view = view;
        this.prefs = prefs;
        this.locationProvider = locationProvider;
    }

    @Override
    public void init() {
        selectedRadioButton = DEFAULT_CATEGORY;
        initFirebaseDatabase();
        view.initCategoryLayout(categoryExpandedState);
        view.disablePublishButton();
        view.initCategoryRadioGroup();
        view.initTitleEditTextListener();
        view.initDescriptionEditTextListener();
        view.initEmailEditTextListener();
        view.initPhoneEditTextListener();
        listenToFieldChanges();
    }

    private void listenToFieldChanges() {
        fieldChangedPublishSubject.observeOn(Schedulers.io())
                .map(ignored -> mandatoryFieldsAreValid())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::refreshPublishButton)
                .subscribe();
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

        // Use push() to create a post unique key in the node containing posts.
        String key = myRef.push().getKey();

        // Upload photo to storage using a Task.
        final StorageReference ref = mStorageRef.child("images/" + key +".jpg");    // Set image name for storage.
        UploadTask uploadTask = ref.putFile(imageUri);

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
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude(),
                    userId,
                    prefs.getUserName(),
                    view.getContactPhone(),
                    getCategory(),
                    view.getEmail());

            // Write a message to the database.
            myRef.child(key).setValue(newPost);
            view.showToast(R.string.post_published_successfully);

        } else {
            view.showToast(R.string.failed_uploading_new_post);
        }
    }

    private String getCategory() {
        String category = DEFAULT_CATEGORY;
        switch (view.getCategoryButtonId()){
            case R.id.electronics_checkbox:
                category = ELECTRONICS_CATEGORY;
                break;
            case R.id.furniture_checkbox:
                category = FURNITURE_CATEGORY;
                break;
            case R.id.books_checkbox:
                category = BOOKS_CATEGORY;
                break;
            case R.id.clothing_checkbox:
                category = CLOTHING_CATEGORY;
                break;
            case R.id.sports_checkbox:
                category = SPORTS_CATEGORY;
                break;
            case R.id.children_checkbox:
                category = CHILDREN_CATEGORY;
                break;
            case R.id.other_checkbox:
                category = OTHER_CATEGORY;
                break;
        }
        return category;
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
        fieldChangedPublishSubject.onNext(IGNORED_VALUE);
    }

    private void refreshPublishButton(Boolean fieldsAreValid) {
        if(fieldsAreValid){
            view.enablePublishButton();
        } else {
            view.disablePublishButton();
        }
    }

    private boolean mandatoryFieldsAreValid() {
        return  (!emptyTitle) &&
                (!emptyDescription) &&
                (validEmail || validPhone) &&
                (!selectedRadioButton.equals(DEFAULT_CATEGORY)) &&
                (currentLocation != null);
    }

    @Override
    public void titleTextChanged(String title) {
        emptyTitle = title.isEmpty();
        fieldChangedPublishSubject.onNext(IGNORED_VALUE);
    }

    @Override
    public void descriptionTextChanged(String description) {
        emptyDescription = description.isEmpty();
        view.setDescriptionLength(description.length(), DESCRIPTION_MAX_LENGTH);
        view.setDescriptionLengthColor(description.length() == DESCRIPTION_MAX_LENGTH ? R.color.red : R.color.charcole);
        fieldChangedPublishSubject.onNext(IGNORED_VALUE);
    }

    @Override
    public void emailTextChanged(String email) {
        validEmail = isValidEmail(email);
        fieldChangedPublishSubject.onNext(IGNORED_VALUE);
    }

    @Override
    public void phoneTextChanged(String phone) {
        validPhone = isValidPhone(phone);
        fieldChangedPublishSubject.onNext(IGNORED_VALUE);
    }

    @Override
    public void onUseCurrentLocationClicked() {
        SingleSubject<Location> locationSubject = SingleSubject.create();
        locationSubject.observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(location -> {
                    currentLocation = location;
                    fieldChangedPublishSubject.onNext(IGNORED_VALUE);
                    view.changeUseCurrentLocationTextViewColor(R.color.green);
                    view.setUseCurrentLocationTextViewUnclickable();
                    view.hideLocationProgressBar();
                }).subscribe();
        view.showLocationProgressBar();
        locationProvider.getLocation(locationSubject);
    }

    private static boolean isValidPhone(String phone) {
        return phone.length() == 10;
    }

    private static boolean isValidEmail(String email) {
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void initFirebaseDatabase() {
        mStorageRef = FirebaseStorage.getInstance().getReference(); // Initialize reference to Storage.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts"); // Reference the 'posts'.
    }
}
