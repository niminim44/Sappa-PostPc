package com.postpc.nimrod.sappa_postpc.repo;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;


import io.reactivex.Single;

public interface Repo {

    Single<List<NearbyPostModel>> getNearbyPostsRx();

    Single<List<MyPostModel>> getMyPostsRx();

}
