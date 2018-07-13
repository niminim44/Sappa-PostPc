package com.postpc.nimrod.sappa_postpc.main.nearby;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

class NearbyPresenter implements NearbyContract.Presenter{


    private NearbyContract.View view;
    private Repo repo;

    NearbyPresenter(NearbyContract.View view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void init() {
        view.showProgressBar();
        repo.getNearbyPostsRx()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::loadPostsToRecyclerView)
                .subscribe();
    }

    private void loadPostsToRecyclerView(List<NearbyPostModel> nearbyPostModels) {
        view.hideProgressBar();
        view.initRecyclerView(nearbyPostModels);
    }

}
