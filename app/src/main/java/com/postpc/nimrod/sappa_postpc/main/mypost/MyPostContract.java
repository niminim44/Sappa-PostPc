package com.postpc.nimrod.sappa_postpc.main.mypost;

import android.os.Bundle;

public interface MyPostContract {

    interface View{

        Bundle getPostArguments();

        float getScreenWidth();

        void setImage(String imageUrl, int screenWidth, int screenHeight);

        void setTitle(String title);

        void setDescription(String description);

        void setCategory(String category);

        void callOnBackPressed();

        void showDeleteProgressBar();
    }

    interface Presenter{

        void init();

        void backButtonClicked();

        void onEditClicked();

        void onDeleteClicked();
    }

}
