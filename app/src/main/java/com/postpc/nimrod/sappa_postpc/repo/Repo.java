package com.postpc.nimrod.sappa_postpc.repo;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;

public interface Repo {

    io.reactivex.Observable<List<NearbyPostModel>> getNearbyPostsRx();

    Observable<List<MyPostModel>> getMyPostsRx();

}
