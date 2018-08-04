package com.postpc.nimrod.sappa_postpc.main.nearbypost;

import android.content.DialogInterface;
import android.os.Bundle;

public interface NearbyPostContract {

    interface View{

        void callOnBackPressed();

        Bundle getPostArguments();

        void setImage(String imageUrl, int screenWidth, int screenHeight);

        void setTitle(String title);

        void setDistance(String distance);

        void setDescription(String description);

        int getScreenHeight();

        int getScreenWidth();

        void setCategory(String category);

        void createAlertDialog(int titleStringResource, String[] items, DialogInterface.OnClickListener onClickListener);

        void callOwner(String phone);

        void messageOwner(String title, String phone);

        void whatsappOwner(String title, String phone);

        void emailOwner(String title, String email);
    }

    interface Presenter{

        void init();

        void backButtonClicked();

        void onNeedItClicked();
    }

}
