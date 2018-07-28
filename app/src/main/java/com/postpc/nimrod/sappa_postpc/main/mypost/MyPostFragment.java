package com.postpc.nimrod.sappa_postpc.main.mypost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostFragment;
import com.postpc.nimrod.sappa_postpc.models.MyPostModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.CATEGORY;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.DESCRIPTION;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.DISTANCE;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.IMAGE_URL;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.LOCATION;
import static com.postpc.nimrod.sappa_postpc.main.mypost.MyPostPresenter.TITLE;

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


    public static MyPostFragment newInstance(MyPostModel myPostModel){
        MyPostFragment myFragment = new MyPostFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, myPostModel.getTitle());
        args.putString(DESCRIPTION, myPostModel.getDescription());
        args.putString(IMAGE_URL, myPostModel.getImageUrl());
        args.putString(CATEGORY, myPostModel.getCategory());
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_post, container, false);
        ButterKnife.bind(this, view);
        presenter = new MyPostPresenter(this);
        presenter.init();
        return view;
    }

    @Override
    public Bundle getPostArguments() {
        return getArguments();
    }
}
