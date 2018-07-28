package com.postpc.nimrod.sappa_postpc.main.nearbypost;

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
    }

    interface Presenter{

        void init();

        void backButtonClicked();
    }

}
