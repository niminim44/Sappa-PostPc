package com.postpc.nimrod.sappa_postpc.main.myposts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.MyPostModel;

import java.util.List;
import java.util.zip.Inflater;

public class MyPostsRecyclerViewAdapter extends RecyclerView.Adapter<MyPostsItemViewHolder> {

    private List<MyPostModel> items;

    public MyPostsRecyclerViewAdapter(List<MyPostModel> items) {
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
