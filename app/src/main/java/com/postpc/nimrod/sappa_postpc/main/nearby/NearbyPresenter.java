package com.postpc.nimrod.sappa_postpc.main.nearby;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

class NearbyPresenter implements NearbyContract.Presenter{


    private NearbyContract.View view;
    private Repo repo;

    NearbyPresenter(NearbyContract.View view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void init() {
        repo.getNearbyPostsRx()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::loadPostsToRecyclerView)
                .subscribe();
    }

    private void loadPostsToRecyclerView(List<NearbyPostModel> nearbyPostModels) {
        view.initRecyclerView(nearbyPostModels);
    }

    private List<NearbyItemViewHolder> getNearbyPosts() {
        return null;
    }
}
