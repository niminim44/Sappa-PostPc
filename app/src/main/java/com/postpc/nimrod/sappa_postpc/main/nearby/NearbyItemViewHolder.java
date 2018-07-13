package com.postpc.nimrod.sappa_postpc.main.nearby;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.events.NearbyPostClickedEvent;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyItemViewHolder extends RecyclerView.ViewHolder {

    private static final int IMAGE_VIEW_WIDTH_IN_DP = 130;
    private static final int IMAGE_VIEW_HEIGHT_IN_DP = 100;


    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.distance_text_view)
    TextView distanceTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.ripple_view)
    View rippleView;

    NearbyItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(NearbyPostModel nearbyPostModel) {
        titleTextView.setText(nearbyPostModel.getTitle());
        distanceTextView.setText(nearbyPostModel.getDistance());
        descriptionTextView.setText(nearbyPostModel.getDescription());
        Glide.with(imageView.getContext())
                .load(nearbyPostModel.getImageUrl())
                .apply(new RequestOptions().override(IMAGE_VIEW_WIDTH_IN_DP, IMAGE_VIEW_HEIGHT_IN_DP))
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
        rippleView.setOnClickListener(getPostClickedListener(nearbyPostModel));
    }

    private View.OnClickListener getPostClickedListener(NearbyPostModel nearbyPostModel) {
        return view -> EventBus.getDefault().post(new NearbyPostClickedEvent(nearbyPostModel));
    }
}
