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

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    // Initialize location provider and get user current location.
    private LocationUtils locationUtils;
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

        //TODO - Data supplier currently irrelevant, data retrieval is done asynchronously in NearbyPresenter by Firebase.
        presenter = new NearbyPresenter(this, new FakeDataSupplier(), new Preferences(requireContext().getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)));

        // Initialize location provider and get user current location.
        locationUtils = new LocationUtils(requireContext(), requireActivity());

        LocationManager lm = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);
        boolean gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        ConnectivityManager cm = (ConnectivityManager)requireContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        String text = "";
        if (!gpsEnabled || !isConnected) {
            text = text + "Please enable";
            if (!gpsEnabled) {
                text = text + "\n - GPS";
            }
            if (!isConnected) {
                text = text + "\n - Wi-Fi or Cellular Data";
            }
            text = text + "\nand restart the application.";
        } else {
            text = text + "Loading posts...please wait:)";
        }

//        CharSequence text = "lat: " + location.getLatitude() + " | lon: " + location.getLongitude();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(requireContext(), text, duration);
        toast.show();

        locationUtils.getDeviceLocation(location -> {

            CharSequence text2 = "My position\nlat: " + location.getLatitude() + " | lon: " + location.getLongitude();
            int duration2 = Toast.LENGTH_LONG;
            Toast toast2 = Toast.makeText(requireContext(), text2, duration2);
            toast2.show();

            presenter.init(location);
        });

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
}
