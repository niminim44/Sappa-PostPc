package com.postpc.nimrod.sappa_postpc.repo.fake;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class FakeDataSupplier implements Repo{

    private static final String FAKE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    private static final String FAKE_DESCRIPTION_2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ";


    @Override
    public Observable<List<NearbyPostModel>> getNearbyPostsRx() {
        return Observable.fromCallable(this::getNearbyPosts)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MyPostModel>> getMyPostsRx() {
        return Observable.fromCallable(this::getMyPosts)
                .subscribeOn(Schedulers.io());
    }

    private List<MyPostModel> getMyPosts() {
        List<MyPostModel> myPosts = new ArrayList<>();
        myPosts.add(new MyPostModel("https://picsum.photos/300/200/?image=95", "DINING TABLE", FAKE_DESCRIPTION, "default"));
        myPosts.add(new MyPostModel("https://picsum.photos/300/200/?image=656", "MOUSE< WORKING", FAKE_DESCRIPTION, "default"));
        myPosts.add(new MyPostModel("https://picsum.photos/300/200/?image=660", "TWO CHAIRS", FAKE_DESCRIPTION, "default"));
        myPosts.add(new MyPostModel("https://picsum.photos/600/400/?image=1013", "COFFEE MUG", FAKE_DESCRIPTION, "default"));
        return myPosts;
    }

    private List<NearbyPostModel> getNearbyPosts() {
        List<NearbyPostModel> nearbyPosts = new ArrayList<>();
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/512/?image=623", "ALMOST NEW BIKE", FAKE_DESCRIPTION_2, "", "3.5 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=660", "TWO CHAIRS", FAKE_DESCRIPTION, "", "5 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=95", "DINING TABLE", FAKE_DESCRIPTION, "", "7.4 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=656", "MOUSE< WORKING", FAKE_DESCRIPTION, "", "10 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=653", "COFFEE MUG", FAKE_DESCRIPTION, "", "10.5 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=274", "ALMOST NEW BIKE", FAKE_DESCRIPTION, "", "3.5 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=1083", "TWO CHAIRS", FAKE_DESCRIPTION, "", "5 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=804", "DINING TABLE", FAKE_DESCRIPTION, "", "7.4 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=885", "MOUSE< WORKING", FAKE_DESCRIPTION, "", "10 miles away", "Sports & Hobbies"));
        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=1013", "COFFEE MUG", FAKE_DESCRIPTION, "", "10.5 miles away", "Sports & Hobbies"));
        return nearbyPosts;
    }


}
