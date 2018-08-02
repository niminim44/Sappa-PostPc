package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.location.Location;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;

public interface NearbyContract {

    interface View{

        void initRecyclerView(List<NearbyPostModel> nearbyPostModels);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter{

        void init(Location location);
    }

}
