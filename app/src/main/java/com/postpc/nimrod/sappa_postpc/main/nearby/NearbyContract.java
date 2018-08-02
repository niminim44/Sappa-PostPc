package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.location.Location;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;

public interface NearbyContract {

    interface View{

        void initRecyclerView(List<NearbyPostModel> nearbyPostModels);

        void showProgressBar();

        void hideProgressBar();

        void showToastMessage(String text);

        void showNoPostsAvailableTextView();
    }

    interface Presenter{

        void init();
    }

}
