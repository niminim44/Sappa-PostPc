package com.postpc.nimrod.sappa_postpc.main.myposts;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;

import java.util.List;

public interface MyPostsContract {

    interface View{

        void showProgressBar();

        void hideProgressBar();

        void initRecyclerView(List<MyPostModel> myPostModels);
    }

    interface Presenter{

        void init();
    }
}
