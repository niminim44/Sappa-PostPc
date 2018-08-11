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
import com.postpc.nimrod.sappa_postpc.main.events.RefreshDataEvent;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationUtils;
import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;
import com.postpc.nimrod.sappa_postpc.models.PostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class NearbyPresenter implements NearbyContract.Presenter{


    private NearbyContract.View view;
    private Preferences preferences;
    private LocationUtils locationUtils;
    private LocationManager locationManager;
    private ConnectivityManager connectivityManager;
    private int range ;
    private EventBus eventBus;


    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    private ArrayList<PostModel> nearbyPostModels = new ArrayList<>();
    private Location currentLocation;
    private boolean servicesAvailable = false;

    NearbyPresenter(NearbyContract.View view, Preferences preferences, LocationUtils locationUtils, ConnectivityManager connectivityManager, EventBus eventBus) {
        this.view = view;
        this.preferences = preferences;
        this.locationUtils = locationUtils;
        this.connectivityManager = connectivityManager;
        range = preferences.getCurrentRangeFilter();
        this.eventBus = eventBus;
    }

    @Override
    public void init() {
        nearbyPostModels = new ArrayList<>();
        subscribeEventBus();
        view.showProgressBar();
        Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(aLong -> currentLocation != null)
                .doOnNext(aLong -> checkForInternetAndGpsConnectivity())
                .subscribe();
    }

    @Override
    public void onDestroy() {
        unsubscribeEventBus();
    }

    private void checkForInternetAndGpsConnectivity() {
        locationManager = view.getLocationManager();
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
            if(!servicesAvailable){
                servicesAvailable = true;
                currentLocation = location;
                getPostsFromServer();
            }
        });
    }

    private void getPostsFromServer() {
        ref.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nearbyPostModels = new ArrayList<>();
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
                Iterable<DataSnapshot> posts = postsSnapShot.getChildren();
                for (DataSnapshot curPost : posts) {
                    PostModel post = curPost.getValue(PostModel.class);

                    // Calculate distance to current item.
                    Location.distanceBetween(myLatitude, myLongitude, post.getLatitude(), post.getLongitude(), dist);
                    post.setDistanceValue(dist[0] * 0.001f);

                    // Filter and convert to NearbyPostModel.
                    if (post.getDistanceValue() < range) {

                        List<CategorySearchModel> categoryFilter = preferences.getCurrentCategoryFilter();
                        String freeTextFilter = preferences.getFreeTextFilter();

                        // Check if current post fits the filtering parameters.
                        boolean notMyPost = checkNotMyPost(post);
                        categoryFits = checkCategoryByFilter(categoryFilter, post.getCategory().toLowerCase());
                        titleFits = post.getTitle().toLowerCase().contains(freeTextFilter.toLowerCase());
                        descriptionFits = post.getDescription().toLowerCase().contains(freeTextFilter.toLowerCase());

                        // Filter current post according to category and search field.
                        // *contains() returns true on empty search
                        if ( categoryFits && ( titleFits || descriptionFits ) && notMyPost) {
                            post.setDistance(Math.round(post.getDistanceValue()) + " km away");
                            nearbyPostModels.add(post);
                        }
                    }
                }
                view.hideProgressBar();
                if(nearbyPostModels.isEmpty()){
                    view.showNoPostsAvailableTextView();
                    view.initRecyclerView(new ArrayList<>());
                }
                else{
//                    Collections.reverse(nearbyPostModels);
                    Collections.sort(nearbyPostModels, (o1, o2) -> {
                        int compareByDate = Long.valueOf(o1.getTimestamp()).compareTo(Long.valueOf(o2.getTimestamp()));
                        if(compareByDate == 0){
                            return Float.compare(o1.getDistanceValue(), o2.getDistanceValue());
                        }
                        return compareByDate;
                    });
                    view.hideNoPostsAvailableTextView();
                    view.initRecyclerView(nearbyPostModels);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ",  databaseError.toString());
            }
        });
    }

    private boolean checkNotMyPost(PostModel post) {
        return !preferences.getUserId().equals(post.getUserID());
    }

    private boolean checkCategoryByFilter(List<CategorySearchModel> categoryFilter, String currentCategory) {
        for(CategorySearchModel category: categoryFilter){
            if(category.getName().toLowerCase().contains(currentCategory) && category.isSelected()){
                return true;
            }
        }
        return false;
    }

    private void retryInFiveSeconds() {
        Observable.timer(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> init()).subscribe();
    }

    private void subscribeEventBus() {
        if(!eventBus.isRegistered(this)){
            eventBus.register(this);
        }
    }

    private void unsubscribeEventBus(){
        if(eventBus.isRegistered(this)){
            eventBus.unregister(this);
        }
    }

    @Subscribe
    public void onRefreshDataEvent(RefreshDataEvent event){
        servicesAvailable = false;
        init();
    }
}
