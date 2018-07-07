package com.postpc.nimrod.sappa_postpc.main.myposts;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

class MyPostsPresenter implements MyPostsContract.Presenter{

    private final Repo repo;
    private final MyPostsContract.View view;

    MyPostsPresenter(MyPostsContract.View view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void init() {
        view.showProgressBar();
        repo.getMyPostsRx()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::loadMyPostsToRecyclerView)
                .subscribe();
    }

    private void loadMyPostsToRecyclerView(List<MyPostModel> myPostModels) {
        view.hideProgressBar();
        view.initRecyclerView(myPostModels);
    }
}
