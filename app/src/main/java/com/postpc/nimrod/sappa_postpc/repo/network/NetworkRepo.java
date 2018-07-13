package com.postpc.nimrod.sappa_postpc.repo.network;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import io.reactivex.schedulers.Schedulers;

public class NetworkRepo implements Repo {
    @Override
    public Observable<List<NearbyPostModel>> getNearbyPostsRx() {
        return Observable.fromCallable(this::getNearbyPosts)
                .subscribeOn(Schedulers.io());
    }

    private List<NearbyPostModel> getNearbyPosts() {
       return new ArrayList<>();
    }

    @Override
    public Observable<List<MyPostModel>> getMyPostsRx() {
        return null;
    }
}
