package com.postpc.nimrod.sappa_postpc.main.settings;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements SettingsContract.View{

    private static final String EXCLAMATION_MARK = "!";
    private static final String SPACE = " ";

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.user_info_expandableLayout)
    ExpandableLinearLayout userInfoExpandedLayout;

    @BindView(R.id.user_info_button)
    ConstraintLayout userInfoButtonLayout;

    @BindView(R.id.user_info_card_view)
    CardView userInfoCardView;

    @BindView(R.id.distance_expandableLayout)
    ExpandableLinearLayout distanceExpandedLayout;

    @BindView(R.id.distance_button)
    ConstraintLayout distanceButtonLayout;

    @BindView(R.id.distance_card_view)
    CardView distanceCardView;

    @BindView(R.id.user_name_text_view)
    TextView userNameTextView;

    @BindView(R.id.user_email_text_view)
    TextView userEmailTextView;

    @BindView(R.id.distance_seek_bar)
    SeekBar distanceSeekBar;

    @BindView(R.id.distance_text_view)
    TextView distanceTextView;

    private SettingsContract.Presenter presenter;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        presenter = new SettingsPresenter(this,
                new Preferences(Objects.requireNonNull(getContext()).getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE)),
                EventBus.getDefault());
        presenter.init();
        return v;
    }


    @Override
    public void setSettingsTitle(String userName, int prefixResourceId) {
        String title = getResources().getString(prefixResourceId) + SPACE + userName + EXCLAMATION_MARK;
        titleTextView.setText(title);
    }

    @Override
    public void initUserInfoLayout(boolean expandedState) {
        userInfoExpandedLayout.setInRecyclerView(false);
        userInfoExpandedLayout.setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        userInfoExpandedLayout.setExpanded(expandedState);
        userInfoExpandedLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(userInfoButtonLayout, 0f, 180f).start();
                presenter.onUserInfoPreOpen();
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(userInfoButtonLayout, 180f, 0f).start();
                presenter.onUserInfoPreClose();
            }
        });
        userInfoButtonLayout.setRotation(expandedState? 180f : 0f);
        userInfoCardView.setOnClickListener(view -> onClickButton(userInfoExpandedLayout));
    }

    @Override
    public void initUserInfo(String userName, String userEmail) {
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);
    }

    @Override
    public void initDistanceSettingsLayout(boolean distanceExpandedState) {
        distanceExpandedLayout.setInRecyclerView(false);
        distanceExpandedLayout.setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        distanceExpandedLayout.setExpanded(distanceExpandedState);
        distanceExpandedLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(distanceButtonLayout, 0f, 180f).start();
                presenter.onDistancePreOpen();
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(distanceButtonLayout, 180f, 0f).start();
                presenter.onDistancePreClose();
            }
        });
        distanceButtonLayout.setRotation(distanceExpandedState? 180f : 0f);
        distanceCardView.setOnClickListener(view -> onClickButton(distanceExpandedLayout));
    }

    @Override
    public void initCurrentDistance(Integer initialProgress, String distanceText) {
        distanceSeekBar.setProgress(initialProgress);
        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentDistance, boolean fromUser) {
                presenter.onDistanceChanged(currentDistance, fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        distanceTextView.setText(distanceText);
    }

    @Override
    public void setDistanceTextView(String distance) {
        distanceTextView.setText(distance);
    }

    private void onClickButton(ExpandableLinearLayout expandableLayout) {
        expandableLayout.toggle();
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @OnClick(R.id.logout_button)
    public void onLogoutClicked(){
        presenter.onLogoutClicked();
    }
}
