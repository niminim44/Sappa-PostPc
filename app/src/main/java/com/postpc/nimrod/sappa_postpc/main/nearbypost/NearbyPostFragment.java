package com.postpc.nimrod.sappa_postpc.main.nearbypost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.postpc.nimrod.sappa_postpc.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyPostFragment extends Fragment implements NearbyPostContract.View{

    @BindView(R.id.image_view)
    ImageView imageView;


    private NearbyPostContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearby_post, container, false);
        presenter = new NearbyPostPresenter(this);
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
}
