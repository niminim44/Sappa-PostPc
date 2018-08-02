package com.postpc.nimrod.sappa_postpc.main.myposts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.PostModel;

import java.util.List;

public class MyPostsRecyclerViewAdapter extends RecyclerView.Adapter<MyPostsItemViewHolder> {

    private List<PostModel> items;

    public MyPostsRecyclerViewAdapter(List<PostModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyPostsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_item_view_holder, parent, false);
        return new MyPostsItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsItemViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
