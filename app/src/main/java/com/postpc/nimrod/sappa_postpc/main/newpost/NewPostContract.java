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
    }

    interface Presenter{

        void init();

        void onCategoryPreOpen();

        void onCategoryPreClose();

        void onPublishClicked();

        void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent);
    }
}
