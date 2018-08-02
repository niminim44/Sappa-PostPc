package com.postpc.nimrod.sappa_postpc.main.nearby;


import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationUtils;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;
import com.postpc.nimrod.sappa_postpc.repo.fake.FakeDataSupplier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements NearbyContract.View{

    private static final String TAG = "NearbyFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.no_available_posts_text_view)
    TextView noAvailablePostsTextView;


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

        presenter = new NearbyPresenter(this, new FakeDataSupplier(),
                new Preferences(requireContext().getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)),
                new LocationUtils(requireContext(), requireActivity()),
                (LocationManager) requireContext().getSystemService(LOCATION_SERVICE),
                (ConnectivityManager)requireContext().getSystemService(CONNECTIVITY_SERVICE));
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

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoPostsAvailableTextView() {
        noAvailablePostsTextView.setVisibility(View.VISIBLE);
    }
}
