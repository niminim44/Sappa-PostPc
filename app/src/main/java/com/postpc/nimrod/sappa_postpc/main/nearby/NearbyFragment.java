package com.postpc.nimrod.sappa_postpc.main.nearby;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.repo.fake.FakeDataSupplier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements NearbyContract.View{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private NearbyContract.Presenter presenter;
    private NearbyRecyclerViewAdapter adapter;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_nearby, container, false);
        ButterKnife.bind(this, v);
        presenter = new NearbyPresenter(this, new FakeDataSupplier());
        presenter.init();
        return v;
    }

    @Override
    public void initRecyclerView(List<NearbyPostModel> nearbyPostModels) {
        adapter = new NearbyRecyclerViewAdapter(nearbyPostModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
