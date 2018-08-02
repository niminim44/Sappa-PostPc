package com.postpc.nimrod.sappa_postpc.main.myposts;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postpc.nimrod.sappa_postpc.models.PostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;
import com.postpc.nimrod.sappa_postpc.repo.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        ref.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<PostModel> myPostModels = new ArrayList<>();

                DataSnapshot postsSnapShot = dataSnapshot.child("posts");

                Iterable<DataSnapshot> posts = postsSnapShot.getChildren();
                for (DataSnapshot curPost : posts) {
                    PostModel post = curPost.getValue(PostModel.class);

                    // Filter and convert to NearbyPostModel.
                    if (myUserId.equals(Objects.requireNonNull(post).getUserID())) {
                        myPostModels.add(post);
                    }
                }
                view.hideProgressBar();
                if(myPostModels.isEmpty()){
                    view.showNoOwnPostsTextView();
                }
                else{
                    view.initRecyclerView(myPostModels);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // TODO - show informational toast like "Something went wrong with connection :( /n please try again later"
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

}
