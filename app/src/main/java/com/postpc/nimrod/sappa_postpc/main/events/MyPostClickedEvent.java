package com.postpc.nimrod.sappa_postpc.main.events;


import com.postpc.nimrod.sappa_postpc.models.PostModel;

public class MyPostClickedEvent {

    private PostModel myPostModel;

    public MyPostClickedEvent(PostModel myPostModel) {
        this.myPostModel = myPostModel;
    }

    public PostModel getMyPostModel() {
        return myPostModel;
    }
}
