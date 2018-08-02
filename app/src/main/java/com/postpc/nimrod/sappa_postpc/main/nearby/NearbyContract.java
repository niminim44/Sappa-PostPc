package com.postpc.nimrod.sappa_postpc.main.nearby;


import com.postpc.nimrod.sappa_postpc.models.PostModel;

import java.util.List;

public interface NearbyContract {

    interface View{

        void initRecyclerView(List<PostModel> nearbyPostModels);

        void showProgressBar();

        void hideProgressBar();

        void showToastMessage(String text);

        void showNoPostsAvailableTextView();

        void hideNoPostsAvailableTextView();
    }

    interface Presenter{

        void init();

        void onDestroy();
    }

}
