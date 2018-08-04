package com.postpc.nimrod.sappa_postpc.main.newpost;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.postpc.nimrod.sappa_postpc.models.PostModel;

public interface NewPostContract {

    interface View{

        void openGallery();

        void openCamera();

        void initCategoryLayout(boolean categoryExpandedState);

        void showToast(int stringResourceId);

        void setImageUri(Uri imageUri);

        String getTitle();

        String getDescription();

        String getContactPhone();

        int getCategoryButtonId();

        void disablePublishButton();

        void initCategoryRadioGroup();

        void setCategoryTitle(String category);

        void initTitleEditTextListener();

        void initDescriptionEditTextListener();

        void initEmailEditTextListener();

        void initPhoneEditTextListener();

        void enablePublishButton();

        void changeUseCurrentLocationTextViewColor(int colorResourceId);

        void showLocationProgressBar();

        void hideLocationProgressBar();

        void setUseCurrentLocationTextViewUnclickable();

        void setDescriptionLength(int length, int maxLength);

        void setDescriptionLengthColor(int colorResourceId);

        String getEmail();

        Context getContext();

        void setImageBitmap(Bitmap thumbnail);

        void hideUploadImageTextView();

        void showPublishProgressBar();

        void callOnBackPressed();

        PostModel getPostToEdit();

        void setTitle(String title);

        void setDescription(String description);

        void selectCategory(int categoryButtonId);

        void setEmail(String email);

        void setPhone(String phone);

        void hideLastLocationTextView();

        void setCurrentLocationTextViewColor(int colorResourceId);

        void setLastLocationTextColor(int colorResourceId);

        void setCurrentLocationClickable();

        void setLastLocationUnclickable();

        void setLastLocationClickable();

        void showLastLocationTextView();

        void showCurrentImageUrl(String imageUrl);
    }

    interface Presenter{

        void init();

        void onCategoryPreOpen();

        void onCategoryPreClose();

        void onPublishClicked();

        void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent);

        void onCategorySelected(int categoryId);

        void titleTextChanged(String s);

        void descriptionTextChanged(String s);

        void emailTextChanged(String email);

        void phoneTextChanged(String phone);

        void onUseCurrentLocationClicked();

        void onLastLocationClicked();
    }
}
