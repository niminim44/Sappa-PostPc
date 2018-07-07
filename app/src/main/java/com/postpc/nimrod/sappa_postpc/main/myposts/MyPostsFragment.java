package com.postpc.nimrod.sappa_postpc.main.myposts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postpc.nimrod.sappa_postpc.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostsFragment extends Fragment {


    public MyPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_my_posts, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

}
