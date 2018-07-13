package com.postpc.nimrod.sappa_postpc.main.myposts;

import android.content.SharedPreferences;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;

class MyPostsPresenter implements MyPostsContract.Presenter{

    private final Repo repo;
    private SharedPreferences preferences;
    private final MyPostsContract.View view;

    MyPostsPresenter(MyPostsContract.View view, Repo repo, SharedPreferences sharedPreferences) {
        this.view = view;
        this.repo = repo;
        preferences = sharedPreferences;
    }

    @Override
    public void init() {
        view.showProgressBar();
        repo.getMyPostsRx()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::loadMyPostsToRecyclerView)
                .subscribe();
    }

    private void loadMyPostsToRecyclerView(List<MyPostModel> myPostModels) {
        view.hideProgressBar();
        view.initRecyclerView(myPostModels);
    }
}
