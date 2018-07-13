package com.postpc.nimrod.sappa_postpc.main.nearbypost;

public interface NearbyPostContract {

    interface View{

        void callOnBackPressed();
    }

    interface Presenter{

        void init();

        void backButtonClicked();
    }

}
