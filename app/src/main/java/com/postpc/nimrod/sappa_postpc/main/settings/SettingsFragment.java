package com.postpc.nimrod.sappa_postpc.main.settings;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements SettingsContract.View{

    private static final String EXCLAMATION_MARK = "!";
    private static final String SPACE = " ";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    private SettingsContract.Presenter presenter;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        presenter = new SettingsPresenter(this, new Preferences(Objects.requireNonNull(getContext()).getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE)));
        presenter.init();
        return v;
    }

    @Override
    public void initRecyclerView(List<SettingModel> settingItems) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SettingsRecyclerViewAdapter(settingItems));
    }

    @Override
    public void setSettingsTitle(String userName, int prefixResourceId) {
        String title = getResources().getString(prefixResourceId) + SPACE + userName + EXCLAMATION_MARK;
        titleTextView.setText(title);
    }
}
