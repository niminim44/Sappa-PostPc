package com.postpc.nimrod.sappa_postpc.main.myposts;

import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NewPostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;

class MyPostsPresenter implements MyPostsContract.Presenter{

    private final Repo repo;
    private Preferences preferences;
    private final MyPostsContract.View view;
    private String myUserId ;

    // Get a reference to our posts.
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();


    MyPostsPresenter(MyPostsContract.View view, Repo repo, Preferences preferences) {
        this.view = view;
        this.repo = repo;
        this.preferences = preferences;
        myUserId = preferences.getUserId();
    }

    @Override
    public void init() {
        view.showProgressBar();
//        repo.getMyPostsRx()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(this::loadMyPostsToRecyclerView)
//                .subscribe();

        ref.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Filtered posts array.
                List<MyPostModel> myPostModels = new ArrayList<>();

                // Get data snapshot of all posts.
                DataSnapshot postsSnapShot = dataSnapshot.child("posts");

                // Iterate over the data snapshot, reproduce NewPostModel for each post
                // and filter it according to distance/category/search parameters.
                //TODO - add category to NewPostModel to filter over it later.
                //TODO - filtering in "My Posts" should be done over UserID from shared preferences.
                Iterable<DataSnapshot> posts = postsSnapShot.getChildren();
                for (DataSnapshot curPost : posts) {
                    NewPostModel post = curPost.getValue(NewPostModel.class);

                    // Filter and convert to NearbyPostModel.
                    if (post.getUserID().compareTo(myUserId) == 0) {
                        myPostModels.add(new MyPostModel(
                                post.getImageUrl(),
                                post.getTitle(),
                                post.getDescription(),
                                post.getCategory()));
                    }
                }

                // Finish data retrieval as in loadPostsToRecyclerView() method.
                view.hideProgressBar();
                view.initRecyclerView(myPostModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // TODO - show informational toast like "Something went wrong with connection :( /n please try again later"
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

//    private void loadMyPostsToRecyclerView(List<MyPostModel> myPostModels) {
//        view.hideProgressBar();
//        view.initRecyclerView(myPostModels);
//    }
}
