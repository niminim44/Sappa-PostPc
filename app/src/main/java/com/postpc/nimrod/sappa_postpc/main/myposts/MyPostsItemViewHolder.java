package com.postpc.nimrod.sappa_postpc.main.myposts;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.MyPostModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPostsItemViewHolder extends RecyclerView.ViewHolder{

    private static final int IMAGE_VIEW_WIDTH_IN_DP = 130;
    private static final int IMAGE_VIEW_HEIGHT_IN_DP = 100;

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    MyPostsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(MyPostModel myPostModel) {
        titleTextView.setText(myPostModel.getTitle());
        descriptionTextView.setText(myPostModel.getDescription());
        Glide.with(imageView.getContext())
                .load(myPostModel.getImageUrl())
                .apply(new RequestOptions().override(IMAGE_VIEW_WIDTH_IN_DP, IMAGE_VIEW_HEIGHT_IN_DP))
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }
}
