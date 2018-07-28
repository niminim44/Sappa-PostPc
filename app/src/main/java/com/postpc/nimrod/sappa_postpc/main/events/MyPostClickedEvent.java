package com.postpc.nimrod.sappa_postpc.main.events;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;

public class MyPostClickedEvent {

    private MyPostModel myPostModel;

    public MyPostClickedEvent(MyPostModel myPostModel) {
        this.myPostModel = myPostModel;
    }

    public MyPostModel getMyPostModel() {
        return myPostModel;
    }
}
