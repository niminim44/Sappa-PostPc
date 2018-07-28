package com.postpc.nimrod.sappa_postpc.main.nearbypost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.CATEGORY;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.DESCRIPTION;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.DISTANCE;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.IMAGE_URL;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.LOCATION;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.TITLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyPostFragment extends Fragment implements NearbyPostContract.View{


    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.distance_text_view)
    TextView distanceTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.category_text_view)
    TextView categoryTextView;


    public static NearbyPostFragment newInstance(NearbyPostModel nearbyPostModel){
        NearbyPostFragment myFragment = new NearbyPostFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, nearbyPostModel.getTitle());
        args.putString(DESCRIPTION, nearbyPostModel.getDescription());
        args.putString(IMAGE_URL, nearbyPostModel.getImageUrl());
        args.putString(DISTANCE, nearbyPostModel.getDistance());
        args.putString(LOCATION, nearbyPostModel.getLocation());
        args.putString(CATEGORY, nearbyPostModel.getCategory());
        myFragment.setArguments(args);
        return myFragment;
    }

    private NearbyPostContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearby_post, container, false);
        ButterKnife.bind(this, v);
        presenter = new NearbyPostPresenter(this, new UiUtils());
        presenter.init();
        return v;
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked(){
        presenter.backButtonClicked();
    }

    @Override
    public void callOnBackPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public Bundle getPostArguments() {
        return getArguments();
    }

    @Override
    public void setImage(String imageUrl, int screenWidth, int screenHeight) {

        CircularProgressDrawable circularProgressBar = new CircularProgressDrawable(imageView.getContext());
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(circularProgressBar))
                .into(imageView);
    }

    @Override
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setDistance(String distance) {
        distanceTextView.setText(distance);
    }

    @Override
    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    @Override
    public int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public void setCategory(String category) {
        categoryTextView.setText(category);
    }
}
