package com.postpc.nimrod.sappa_postpc.main.settings;

import com.facebook.login.LoginManager;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.events.LogoutEvent;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

class SettingsPresenter implements SettingsContract.Presenter{

    private static final String BULLET = "\u2022 ";
    private SettingsContract.View view;
    private Preferences preferences;
    private EventBus eventBus;

    private boolean userInfoExpandedState = false;
    private boolean distanceExpandedState = false;
    private boolean aboutExpandedState = false;

    SettingsPresenter(SettingsContract.View view, Preferences preferences, EventBus eventBus) {
        this.view = view;
        this.preferences = preferences;
        this.eventBus = eventBus;
    }


    @Override
    public void init() {
        view.setSettingsTitle(preferences.getUserName(), R.string.hi);
        view.initUserInfoLayout(userInfoExpandedState);
        view.initUserInfo(BULLET + preferences.getUserName(), BULLET + preferences.getUserEmail());
        view.initDistanceSettingsLayout(distanceExpandedState);
        Integer currentDistanceFilter = preferences.getCurrentRangeFilter();
        view.initCurrentDistance(currentDistanceFilter, currentDistanceFilter.toString() + " km");
        view.initAboutLayout(aboutExpandedState);
    }

    @Override
    public void onUserInfoPreOpen() {
        userInfoExpandedState = true;
    }

    @Override
    public void onUserInfoPreClose() {
        userInfoExpandedState = false;
    }

    @Override
    public void onLogoutClicked() {
        preferences.clearAllFields();
        LoginManager.getInstance().logOut();
        eventBus.post(new LogoutEvent());
    }

    @Override
    public void onDistancePreOpen() {
        distanceExpandedState = true;
    }

    @Override
    public void onDistancePreClose() {
        distanceExpandedState = false;
    }

    @Override
    public void onDistanceChanged(int currentDistance, boolean fromUser) {
        preferences.saveCurrentRangeFilter(currentDistance);
        view.setDistanceTextView(currentDistance + " km");
    }

    @Override
    public void onAboutPreOpen() {
        aboutExpandedState = true;
    }

    @Override
    public void onAboutPreClose() {
        aboutExpandedState = false;
    }

}
