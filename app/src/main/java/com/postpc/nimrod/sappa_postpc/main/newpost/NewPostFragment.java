package com.postpc.nimrod.sappa_postpc.main.newpost;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.google.gson.Gson;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationProvider;
import com.postpc.nimrod.sappa_postpc.models.PostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * The new post fragment.
 */
public class NewPostFragment extends Fragment implements NewPostContract.View{

    private static final String POST_TO_EDIT = "post to edit";
    private static final String IS_EDIT_MODE = "is edit mode";
    @BindView(R.id.category_button)
    ConstraintLayout buttonLayout;

    @BindView(R.id.category_view)
    CardView categoryView;

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_edit_text)
    EditText titleEditText;

    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;

    @BindView(R.id.expandableLayout)
    ExpandableLinearLayout expandableLayout;

    @BindView(R.id.publish_button)
    Button publishButton;

    @BindView(R.id.category_radio_group)
    RadioGroup categoryRadioGroup;

    @BindView(R.id.category_title_text_view)
    TextView categoryTitleTextView;

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.phone_number_edit_text)
    EditText phoneEditText;

    @BindView(R.id.user_current_location_text_view)
    TextView useCurrentLocationTextView;

    @BindView(R.id.last_location_text_view)
    TextView lastLocationTextView;

    @BindView(R.id.or_text_view)
    TextView orTextView;

    @BindView(R.id.location_progress_bar)
    ProgressBar locationProgressBar;

    @BindView(R.id.description_length_text_view)
    TextView descriptionLengthTextView;

    @BindView(R.id.upload_image_text_view)
    TextView uploadImageButton;

    @BindView(R.id.publish_progress_bar)
    ProgressBar publishProgressBar;



    private NewPostContract.Presenter presenter;
    private String currentPhotoPath;


    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_post_new, container, false);
        ButterKnife.bind(this, v);
        presenter = new NewPostPresenter(this,
                new Preferences(requireContext().getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)),
                new LocationProvider(requireContext()),
                EventBus.getDefault());
        presenter.init();
        return v;
    }

    @Override
    public void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 0);
    }

    @Override
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        getContext().getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }


//
//        Intent takePicture  = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//
//
//        startActivityForResult(takePicture , 1);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        presenter.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

    @Override
    public void initCategoryLayout(boolean categoryExpandedState) {
        expandableLayout.setInRecyclerView(false);
        expandableLayout.setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR));
        expandableLayout.setExpanded(categoryExpandedState);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(buttonLayout, 0f, 180f).start();
                presenter.onCategoryPreOpen();
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(buttonLayout, 180f, 0f).start();
                presenter.onCategoryPreClose();
            }
        });
        buttonLayout.setRotation(categoryExpandedState ? 180f : 0f);
        categoryView.setOnClickListener(view -> onClickButton(expandableLayout));
    }

    @Override
    public void showToast(int stringResourceId) {
        Toast.makeText(getActivity(), getResources().getString(stringResourceId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageUri(Uri imageUri) {
        Glide.with(imageView.getContext())
                .load(imageUri)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public String getTitle() {
        return titleEditText.getText().toString();
    }

    @Override
    public String getDescription() {
        return descriptionEditText.getText().toString();
    }

    @Override
    public String getContactPhone() {
        return phoneEditText.getText().toString();
    }

    @Override
    public int getCategoryButtonId() {
        return categoryRadioGroup.getCheckedRadioButtonId();
    }

    @Override
    public void disablePublishButton() {
        publishButton.setEnabled(false);
    }

    @Override
    public void initCategoryRadioGroup() {
        categoryRadioGroup.clearCheck();
        categoryRadioGroup.setOnCheckedChangeListener((group, categoryId) -> {
            presenter.onCategorySelected(categoryId);
        });
    }

    @Override
    public void setCategoryTitle(String category) {
        categoryTitleTextView.setText(category);
    }

    @Override
    public void initTitleEditTextListener() {
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.titleTextChanged(editable.toString());
            }

        });
    }

    @Override
    public void initDescriptionEditTextListener() {
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.descriptionTextChanged(editable.toString());
            }
        });
    }

    @Override
    public void initEmailEditTextListener() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.emailTextChanged(editable.toString());
            }
        });
    }

    @Override
    public void initPhoneEditTextListener() {
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.phoneTextChanged(editable.toString());
            }
        });
    }

    @Override
    public void enablePublishButton() {
        publishButton.setEnabled(true);
    }

    @Override
    public void changeUseCurrentLocationTextViewColor(int colorResourceId) {
        useCurrentLocationTextView.setTextColor(getResources().getColor(colorResourceId));
    }

    @Override
    public void showLocationProgressBar() {
        locationProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLocationProgressBar() {
        locationProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUseCurrentLocationTextViewUnclickable() {
        useCurrentLocationTextView.setClickable(false);
    }

    @Override
    public void setDescriptionLength(int length, int maxLength) {
        String lengthString = String.valueOf(length) + "/" + String.valueOf(maxLength);
        descriptionLengthTextView.setText(lengthString);
    }

    @Override
    public void setDescriptionLengthColor(int colorResourceId) {
        descriptionLengthTextView.setTextColor(getResources().getColor(colorResourceId));
    }

    @Override
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public void setImageBitmap(Bitmap thumbnail) {
        Glide.with(imageView.getContext())
                .load(thumbnail)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUploadImageTextView() {
        uploadImageButton.setVisibility(View.GONE);
    }

    @Override
    public void showPublishProgressBar() {
        publishButton.setVisibility(View.INVISIBLE);
        publishProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void callOnBackPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public PostModel getPostToEdit() {
        Bundle args = getArguments();
        if(args != null && args.getBoolean(IS_EDIT_MODE, false)){
            return (new Gson()).fromJson(args.getString(POST_TO_EDIT), PostModel.class);
        }
        return null;
    }

    @Override
    public void setTitle(String title) {
        titleEditText.setText(title);
    }

    @Override
    public void setDescription(String description) {
        descriptionEditText.setText(description);
    }

    @Override
    public void selectCategory(int categoryButtonId) {
        categoryRadioGroup.check(categoryButtonId);
    }

    @Override
    public void setEmail(String email) {
        if(email != null){
            emailEditText.setText(email);
        }
    }

    @Override
    public void setPhone(String phone) {
        if(phone != null){
            phoneEditText.setText(phone);
        }
    }

    @Override
    public void hideLastLocationTextView() {
        lastLocationTextView.setVisibility(View.GONE);
        orTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setCurrentLocationTextViewColor(int colorResourceId) {
        useCurrentLocationTextView.setTextColor(getResources().getColor(colorResourceId));
    }

    @Override
    public void setLastLocationTextColor(int colorResourceId) {
        lastLocationTextView.setTextColor(getResources().getColor(colorResourceId));
    }

    @Override
    public void setCurrentLocationClickable() {
        useCurrentLocationTextView.setClickable(true);
    }

    @Override
    public void setLastLocationUnclickable() {
        lastLocationTextView.setClickable(false);
    }

    @Override
    public void setLastLocationClickable() {
        lastLocationTextView.setClickable(true);
    }

    @Override
    public void showLastLocationTextView() {
        lastLocationTextView.setVisibility(View.VISIBLE);
        orTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCurrentImageUrl(String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadImageFromPath() {
        Glide.with(imageView.getContext())
                .load(currentPhotoPath)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    @OnClick(R.id.publish_button)
    public void onPublishClicked(){
        presenter.onPublishClicked();
    }

    @OnClick(R.id.user_current_location_text_view)
    public void onUseCurrentLocationClicked(){
        presenter.onUseCurrentLocationClicked();
    }

    @OnClick(R.id.description_card_view)
    public void onDescriptionClicked(){
        focusDescriptionAndOpenKeyboard();
    }

    @OnClick(R.id.upload_image_text_view)
    public void onUploadImageClicked(){
        showPictureDialog();
    }

    @OnClick(R.id.last_location_text_view)
    public void onLastLocationClicked(){
        presenter.onLastLocationClicked();
    }

    @OnClick(R.id.image_view)
    public void onImageViewClicked(){
        showPictureDialog();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            openGallery();
                            break;
                        case 1:
                            openCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    private void focusDescriptionAndOpenKeyboard() {
        descriptionEditText.requestFocus();
        try{
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(descriptionEditText, InputMethodManager.SHOW_IMPLICIT);
        }
        catch (Exception ignored){}
    }


    private void onClickButton(ExpandableLinearLayout expandableLayout) {
        expandableLayout.toggle();
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public static NewPostFragment newInstance(PostModel postModel) {
        NewPostFragment newPostFragment= new NewPostFragment();
        Bundle args = new Bundle();
        String postModelJson = (new Gson()).toJson(postModel);
        args.putString(POST_TO_EDIT, postModelJson);
        args.putBoolean(IS_EDIT_MODE, true);
        newPostFragment.setArguments(args);
        return newPostFragment;
    }
}
