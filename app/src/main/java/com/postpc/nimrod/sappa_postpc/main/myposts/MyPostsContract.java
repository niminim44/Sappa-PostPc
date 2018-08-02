package com.postpc.nimrod.sappa_postpc.main.myposts;

import com.postpc.nimrod.sappa_postpc.models.PostModel;

import java.util.List;

public interface MyPostsContract {

    interface View{

        void showProgressBar();

        void hideProgressBar();

        void initRecyclerView(List<PostModel> myPostModels);

        void showNoOwnPostsTextView();

        void hideNoOwnPostsTextView();
    }

    interface Presenter{

        void init();

        void onDestroy();
    }
}
