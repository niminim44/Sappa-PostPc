package com.postpc.nimrod.sappa_postpc.repo.network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NetworkRepo{
//    @Override
//    public Observable<List<NearbyPostModel>> getNearbyPostsRx() {
//        return Observable.fromCallable(this::getNearbyPosts)
//                .subscribeOn(Schedulers.io());
//    }
//
//    private List<NearbyPostModel> getNearbyPosts() {
//       return new ArrayList<>();
//    }
//
//    @Override
//    public Observable<List<MyPostModel>> getMyPostsRx() {
//        return null;
//    }


    private static final String FAKE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
//
//    @Override
//    public Observable<List<NearbyPostModel>> getNearbyPostsRx() {
//        return Observable.fromCallable(this::getNearbyPosts)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Observable<List<MyPostModel>> getMyPostsRx() {
//        return Observable.fromCallable(this::getMyPosts)
//                .subscribeOn(Schedulers.io());
//    }
//
//    private List<MyPostModel> getMyPosts() {
//        List<MyPostModel> myPosts = new ArrayList<>();
//        myPosts.add(new MyPostModel("https://picsum.photos/300/200/?image=95", "DINING TABLE", FAKE_DESCRIPTION, "default"));
//        myPosts.add(new MyPostModel("https://picsum.photos/300/200/?image=656", "MOUSE< WORKING", FAKE_DESCRIPTION, "default"));
//        myPosts.add(new MyPostModel("https://picsum.photos/300/200/?image=660", "TWO CHAIRS", FAKE_DESCRIPTION, "default"));
//        myPosts.add(new MyPostModel("https://picsum.photos/600/400/?image=1013", "COFFEE MUG", FAKE_DESCRIPTION, "default"));
//        return myPosts;
//    }
//
//    private List<NearbyPostModel> getNearbyPosts() {
//        List<NearbyPostModel> nearbyPosts = new ArrayList<>();
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=573", "ALMOST NEW BIKE", FAKE_DESCRIPTION, "", "3.5 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=660", "TWO CHAIRS", FAKE_DESCRIPTION, "", "5 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=95", "DINING TABLE", FAKE_DESCRIPTION, "", "7.4 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=656", "MOUSE< WORKING", FAKE_DESCRIPTION, "", "10 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/300/200/?image=653", "COFFEE MUG", FAKE_DESCRIPTION, "", "10.5 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=274", "ALMOST NEW BIKE", FAKE_DESCRIPTION, "", "3.5 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=1083", "TWO CHAIRS", FAKE_DESCRIPTION, "", "5 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=804", "DINING TABLE", FAKE_DESCRIPTION, "", "7.4 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=885", "MOUSE< WORKING", FAKE_DESCRIPTION, "", "10 miles away", "default category"));
//        nearbyPosts.add(new NearbyPostModel("https://picsum.photos/600/400/?image=1013", "COFFEE MUG", FAKE_DESCRIPTION, "", "10.5 miles away", "default category"));
//        return nearbyPosts;
//    }




}
