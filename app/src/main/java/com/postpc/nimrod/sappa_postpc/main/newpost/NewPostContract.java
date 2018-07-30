package com.postpc.nimrod.sappa_postpc.main.newpost;

import android.content.Intent;
import android.net.Uri;

public interface NewPostContract {

    interface View{

        void openGallery();

        void openCamera();

        void initCategoryLayout(boolean categoryExpandedState);

        void showToast(int stringResourceId);

        void setImageUri(Uri imageUri);

        String getTitle();

        String getDescription();

        String getContactName();

        String getContactPhone();

        String getCategory();

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
    }
}
