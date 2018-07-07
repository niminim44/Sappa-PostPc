package com.postpc.nimrod.sappa_postpc.repo.fake;

import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FakeDataSupplier implements Repo{

    private static final String FAKE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

    @Override
    public Single<List<NearbyPostModel>> getNearbyPostsRx() {
        return Single.just(true)
                .delay(5000, TimeUnit.MILLISECONDS)
                .map(this::getNearbyPosts)
                .subscribeOn(Schedulers.io());
    }

    private List<NearbyPostModel> getNearbyPosts(Boolean ignored) {
        List<NearbyPostModel> nearbyPosts = new ArrayList<>();
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=573", "ALMOST NEW BIKE", FAKE_DESCRIPTION, "", "3.5 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=660", "TWO CHAIRS", FAKE_DESCRIPTION, "", "5 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=95", "DINING TABLE", FAKE_DESCRIPTION, "", "7.4 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=656", "MOUSE< WORKING", FAKE_DESCRIPTION, "", "10 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=653", "COFFEE MUG", FAKE_DESCRIPTION, "", "10.5 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=274", "ALMOST NEW BIKE", FAKE_DESCRIPTION, "", "3.5 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=1083", "TWO CHAIRS", FAKE_DESCRIPTION, "", "5 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=804", "DINING TABLE", FAKE_DESCRIPTION, "", "7.4 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=885", "MOUSE< WORKING", FAKE_DESCRIPTION, "", "10 miles away"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=1013", "COFFEE MUG", FAKE_DESCRIPTION, "", "10.5 miles away"));
        return nearbyPosts;
    }


}
