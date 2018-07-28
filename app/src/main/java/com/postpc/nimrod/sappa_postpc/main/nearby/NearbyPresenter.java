package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.location.Location;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NewPostModel;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;

class NearbyPresenter implements NearbyContract.Presenter{


    private NearbyContract.View view;
    private Repo repo;

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    NearbyPresenter(NearbyContract.View view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void init() {
        view.showProgressBar();
//        repo.getNearbyPostsRx()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(this::loadPostsToRecyclerView)
//                .subscribe();

        // Retrieve data from Firebase Realtime DB (done asynchronously by Firebase).
        // Attach single time listener to read the data from our posts reference.
        // Basically this init() method starts the data retrieval and continues
        // after we get and filter the result (hides the loading wheel and initializes
        // the recycler view).
        ref.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Filtered posts array.
                List<NearbyPostModel> nearbyPostModels = new ArrayList<>();

                // Set default location (Givat Ram - Computer Science  building)
                double myLatitude = 31.776700;
                double myLongitude = 35.197507;
                float [] dist = new float[1];   // Distance calculation container.

                // Set range.
                //TODO - get range parameter from shared preferences.
                float range = 10;

                // Get data snapshot of all posts.
                DataSnapshot postsSnapShot = dataSnapshot.child("posts");

                // Iterate over the data snapshot, reproduce NewPostModel for each post
                // and filter it according to distance/category/search parameters.
                //TODO - add category to NewPostModel to filter over it later.
                //TODO - filtering in "My Posts" should be done over UserID from shared preferences.
                Iterable<DataSnapshot> posts = postsSnapShot.getChildren();
                for (DataSnapshot curPost : posts) {
                    NewPostModel post = curPost.getValue(NewPostModel.class);

                    // Calculate distance to current item.
                    Location.distanceBetween(myLatitude, myLongitude, post.getLatitude(), post.getLongitude(), dist);

                    // Filter and convert to NearbyPostModel.
                    if ((dist[0] * 0.001) < range) {
                        nearbyPostModels.add(new NearbyPostModel(post.getImageUrl(), post.getTitle(), post.getDescription(), "", Math.round(dist[0] * 0.001) + " km away", post.getCategory()));
                    }
                }

                // Finish data retrieval as in loadPostsToRecyclerView() method.
                view.hideProgressBar();
                view.initRecyclerView(nearbyPostModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // TODO - show informational toast like "Something went wrong with connection :( /n please try again later"
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }


//    private void loadPostsToRecyclerView(List<NearbyPostModel> nearbyPostModels) {
//        view.hideProgressBar();
//        view.initRecyclerView(nearbyPostModels);
//    }

}
