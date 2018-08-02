package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationUtils;
import com.postpc.nimrod.sappa_postpc.models.PostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class NearbyPresenter implements NearbyContract.Presenter{


    private NearbyContract.View view;
    private Repo repo;
    private Preferences preferences;
    private LocationUtils locationUtils;
    private LocationManager locationManager;
    private ConnectivityManager connectivityManager;
    private int range ;


    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    private ArrayList<PostModel> nearbyPostModels = new ArrayList<>();
    private Location currentLocation;

    NearbyPresenter(NearbyContract.View view, Repo repo, Preferences preferences, LocationUtils locationUtils,
                    LocationManager locationManager, ConnectivityManager connectivityManager) {
        this.view = view;
        this.repo = repo;
        this.preferences = preferences;
        this.locationUtils = locationUtils;
        this.locationManager = locationManager;
        this.connectivityManager = connectivityManager;
        range = preferences.getCurrentRangeFilter();
    }

    @Override
    public void init() {
        view.showProgressBar();
        checkForInternetAndGpsConnectivity();
    }

    private void checkForInternetAndGpsConnectivity() {
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        String text = "";
        if (!gpsEnabled || !isConnected) {
            askUserToTurnOnInternetOrGps(gpsEnabled, isConnected, text);
            retryInFiveSeconds();
        } else {
            getLocationAndContinue();
        }
    }

    private void askUserToTurnOnInternetOrGps(boolean gpsEnabled, boolean isConnected, String text) {
        text = text + "Please enable";
        if (!gpsEnabled) {
            text = text + "\n - GPS";
        }
        if (!isConnected) {
            text = text + "\n - Wi-Fi or Cellular Data";
        }
        text = text + "\nand restart the application.";
        view.showToastMessage(text);
    }

    private void getLocationAndContinue() {
        locationUtils.getDeviceLocation(location -> {
            currentLocation = location;
            getPostsFromServer();
        });
    }

    private void getPostsFromServer() {
        ref.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Set default location (Givat Ram - Computer Science  building)
                double myLatitude = currentLocation.getLatitude();
                double myLongitude = currentLocation.getLongitude();
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
                    PostModel post = curPost.getValue(PostModel.class);

                    // Calculate distance to current item.
                    Location.distanceBetween(myLatitude, myLongitude, post.getLatitude(), post.getLongitude(), dist);

                    // Filter and convert to NearbyPostModel.
                    if ((dist[0] * 0.001) < range) {

                        //TODO - values we need to store in preferences:
                        String categoryFilter = preferences.getCurrentCategoryFilter();
                        String titleFilter = "tv";
                        String descriptionFilter = "other";

                        // Check if current post fits the filtering parameters.
                        categoryFits = categoryFilter.toLowerCase().contains(post.getCategory().toLowerCase());
                        titleFits = titleFilter.toLowerCase().contains(post.getTitle().toLowerCase());
                        descriptionFits = descriptionFilter.toLowerCase().contains(post.getDescription().toLowerCase());

                        // Filter current post according to category and search field.
                        // *contains() returns true on empty search
                        if ( categoryFits && ( titleFits || descriptionFits ) ) {
                            post.setDistance(Math.round(dist[0] * 0.001) + " km away");
                            nearbyPostModels.add(post);
                        }
                    }
                }
                view.hideProgressBar();
                if(nearbyPostModels.isEmpty()){
                    view.showNoPostsAvailableTextView();
                }
                else{
                    view.initRecyclerView(nearbyPostModels);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ",  databaseError.toString());
            }
        });
    }

    private void retryInFiveSeconds() {
        Observable.timer(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> init()).subscribe();
    }
}
