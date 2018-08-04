package com.postpc.nimrod.sappa_postpc.main.events;

import com.postpc.nimrod.sappa_postpc.models.PostModel;

public class EditPostEvent {

    private PostModel postModel;

    public EditPostEvent(PostModel postModel) {
        this.postModel = postModel;
    }

    public PostModel getPostModel() {
        return postModel;
    }
}
