package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;
import java.util.zip.Inflater;

public class NearbyRecyclerViewAdapter extends RecyclerView.Adapter<NearbyItemViewHolder>{

    private List<NearbyPostModel> items;

    public NearbyRecyclerViewAdapter(List<NearbyPostModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NearbyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item_view_holder, parent, false);
        return new NearbyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyItemViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
