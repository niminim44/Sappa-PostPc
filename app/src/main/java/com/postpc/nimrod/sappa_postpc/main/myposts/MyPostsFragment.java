package com.postpc.nimrod.sappa_postpc.main.myposts;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.PostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostsFragment extends Fragment implements MyPostsContract.View{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.no_own_posts_text_view)
    TextView noOwnPostsTextView;


    private MyPostsContract.Presenter presenter;

    public MyPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_my_posts, container, false);
        ButterKnife.bind(this, v);
        presenter = new MyPostsPresenter(this,
                new Preferences(requireContext().getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)),
                EventBus.getDefault());
        presenter.init();
        return v;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void initRecyclerView(List<PostModel> myPostModels) {
        MyPostsRecyclerViewAdapter adapter = new MyPostsRecyclerViewAdapter(myPostModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoOwnPostsTextView() {
        noOwnPostsTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoOwnPostsTextView() {
        noOwnPostsTextView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
