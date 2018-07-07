package com.postpc.nimrod.sappa_postpc.repo.local;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.List;

import io.reactivex.Single;

public class LocalRepo implements Repo{


    @Override
    public Single<List<NearbyPostModel>> getNearbyPostsRx() {
        return null;
    }

    @Override
    public Single<List<MyPostModel>> getMyPostsRx() {
        return null;
    }
}
