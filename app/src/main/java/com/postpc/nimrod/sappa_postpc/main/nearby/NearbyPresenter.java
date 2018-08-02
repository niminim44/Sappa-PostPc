package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NewPostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;

class NearbyPresenter implements NearbyContract.Presenter{


    private NearbyContract.View view;
    private Repo repo;
    private Preferences preferences;
    private int range ;


    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    NearbyPresenter(NearbyContract.View view, Repo repo, Preferences preferences) {
        this.view = view;
        this.repo = repo;
        this.preferences = preferences;
        range = 10;   //TODO - range value we need to store in preferences and replace this line with "preferences.getRange()"

    }

    @Override
    public void init(Location location) {
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
                double myLatitude = location.getLatitude();
                double myLongitude = location.getLongitude();
                float [] dist = new float[1];   // Distance calculation container.

                // Get data snapshot of all posts.
                DataSnapshot postsSnapShot = dataSnapshot.child("posts");

                // Variables for filtering.
                boolean categoryFits;
                boolean titleFits;
                boolean descriptionFits;

                // Iterate over the data snapshot, reproduce NewPostModel for each post
                // and filter it according to distance/category/search parameters.
                //TODO - add category to NewPostModel to filter over it later.
                Iterable<DataSnapshot> posts = postsSnapShot.getChildren();
                for (DataSnapshot curPost : posts) {
                    NewPostModel post = curPost.getValue(NewPostModel.class);

                    // Calculate distance to current item.
                    Location.distanceBetween(myLatitude, myLongitude, post.getLatitude(), post.getLongitude(), dist);

                    // Filter and convert to NearbyPostModel.
                    if ((dist[0] * 0.001) < range) {

                        //TODO - values we need to store in preferences:
                        String categoryFilter = "other";
                        String titleFilter = "tv";
                        String descriptionFilter = "other";

                        // Check if current post fits the filtering parameters.
                        categoryFits = categoryFilter.toLowerCase().contains(post.getCategory().toLowerCase());
                        titleFits = titleFilter.toLowerCase().contains(post.getTitle().toLowerCase());
                        descriptionFits = descriptionFilter.toLowerCase().contains(post.getDescription().toLowerCase());

                        // Filter current post according to category and search field.
                        // *contains() returns true on empty search
                        if ( categoryFits && ( titleFits || descriptionFits ) ) {
                                nearbyPostModels.add(new NearbyPostModel(
                                        post.getImageUrl(),
                                        post.getTitle(),
                                        post.getDescription(),
                                        "",
                                        Math.round(dist[0] * 0.001) + " km away",
                                        post.getCategory()));
                        }
                    }
                }

                // Finish data retrieval as in loadPostsToRecyclerView() method.
                view.hideProgressBar();
                view.initRecyclerView(nearbyPostModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ",  databaseError.toString());
            }
        });

    }


}
