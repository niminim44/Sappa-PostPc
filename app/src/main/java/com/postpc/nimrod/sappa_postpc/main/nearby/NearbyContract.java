package com.postpc.nimrod.sappa_postpc.main.nearby;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;

public interface NearbyContract {

    interface View{

        void initRecyclerView(List<NearbyPostModel> nearbyPostModels);
    }

    interface Presenter{

        void init();
    }

}
