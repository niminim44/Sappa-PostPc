package com.postpc.nimrod.sappa_postpc.repo.local;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;

public class LocalRepo implements Repo{


    @Override
    public Observable<List<NearbyPostModel>> getNearbyPostsRx() {
        return null;
    }

    @Override
    public Observable<List<MyPostModel>> getMyPostsRx() {
        return null;
    }
}
