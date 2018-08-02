package com.postpc.nimrod.sappa_postpc.main.mypost;


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
import com.postpc.nimrod.sappa_postpc.models.PostModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.CATEGORY;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.DESCRIPTION;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.EMAIL;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.IMAGE_URL;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.LATITUDE;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.LONGITUDE;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.PHONE;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.POST_ID;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.TITLE;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.USER_ID;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.USER_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment implements MyPostContract.View{

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.category_text_view)
    TextView categoryTextView;
    private MyPostContract.Presenter presenter;


    public static MyPostFragment newInstance(PostModel myPostModel){
        MyPostFragment myFragment = new MyPostFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, myPostModel.getTitle());
        args.putString(DESCRIPTION, myPostModel.getDescription());
        args.putString(IMAGE_URL, myPostModel.getImageUrl());
        args.putString(CATEGORY, myPostModel.getCategory());
        args.putString(USER_ID, myPostModel.getUserID());
        args.putString(USER_NAME, myPostModel.getUserName());
        args.putDouble(LATITUDE, myPostModel.getLatitude());
        args.putDouble(LONGITUDE, myPostModel.getLongitude());
        args.putString(PHONE, myPostModel.getPhone());
        args.putString(EMAIL, myPostModel.getEmail());
        args.putString(POST_ID, myPostModel.getPostId());
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_post, container, false);
        ButterKnife.bind(this, view);
        presenter = new MyPostPresenter(this, new UiUtils());
        presenter.init();
        return view;
    }

    @Override
    public Bundle getPostArguments() {
        return getArguments();
    }

    @Override
    public float getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
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
    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    @Override
    public void setCategory(String category) {
        categoryTextView.setText(category);
    }

    @Override
    public void callOnBackPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked(){
        presenter.backButtonClicked();
    }

    @OnClick(R.id.edit_post_button)
    public void onEditClicked(){
        presenter.onEditClicked();
    }

    @OnClick(R.id.delete_post_button)
    public void onDeleteClicked(){
        presenter.onDeleteClicked();
    }
}
