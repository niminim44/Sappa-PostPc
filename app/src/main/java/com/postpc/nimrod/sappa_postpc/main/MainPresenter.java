package com.postpc.nimrod.sappa_postpc.main;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.events.LogoutEvent;
import com.postpc.nimrod.sappa_postpc.main.events.MyPostClickedEvent;
import com.postpc.nimrod.sappa_postpc.main.events.NearbyPostClickedEvent;
import com.postpc.nimrod.sappa_postpc.main.myposts.MyPostsFragment;
import com.postpc.nimrod.sappa_postpc.main.nearby.NearbyFragment;
import com.postpc.nimrod.sappa_postpc.main.settings.SettingsFragment;
import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class MainPresenter implements MainContract.Presenter{

    private static final int INITIAL_TAB_LAYOUT_POSITION = 0;
    private static final int NEARBY_POSITION = 0;
    private static final int MYPOSTS_POSITION = 1;
    private static final int SETTINGS_POSITION = 2;
    private static final float FAB_MARGIN_IN_DP = 10;
    private final Preferences preferences;
    private UiUtils uiUtils;
    private EventBus eventBus;
    private MainContract.View view;
    private float fabMarginsInPx;
    private int currentPagePosition;

    MainPresenter(MainContract.View view, Preferences preferences, UiUtils uiUtils, EventBus eventBus) {
        this.view = view;
        this.preferences = preferences;
        this.uiUtils = uiUtils;
        this.eventBus = eventBus;
    }

    @Override
    public void init() {
        subscribeEventBus();
        calcFabMarginsInPixels();
        view.setUserNameTextView(preferences.getUserName());
        view.setViewPagerAndTabsLayout(getViewPagerFragments(), getTabsLayoutsIds(),
                getTabSelectedListener(), INITIAL_TAB_LAYOUT_POSITION);
    }

    private void subscribeEventBus() {
        if(!eventBus.isRegistered(this)){
            eventBus.register(this);
        }
    }

    private void unsubscribeEventBus(){
        if(eventBus.isRegistered(this)){
            eventBus.unregister(this);
        }
    }

    private void calcFabMarginsInPixels() {
        fabMarginsInPx = uiUtils.convertDpToPixel(FAB_MARGIN_IN_DP);
    }

    @Override
    public void onFabClicked() {
        switch (currentPagePosition){
            case NEARBY_POSITION:
                break;
            case MYPOSTS_POSITION:
                view.slideDownFab(fabMarginsInPx);
                view.openNewPostFragment();
                break;
            case SETTINGS_POSITION:
                break;
        }
    }

    @Override
    public void destroy() {
        unsubscribeEventBus();
    }

    @Override
    public void setCurrentPage(int currentPagePosition) {
        this.currentPagePosition = currentPagePosition;
    }

    @Override
    public void onBackPressed() {
        view.slideUpFab(fabMarginsInPx);
    }

    @Subscribe
    public void onNearbyPostClicked(NearbyPostClickedEvent event){
        view.openNearbyPostFragment(event.getNearbyPostModel());
        view.slideDownFab(fabMarginsInPx);
    }

    @Subscribe
    public void onMyPostClicked(MyPostClickedEvent event){
        view.openMyPostFragment(event.getMyPostModel());
        view.slideDownFab(fabMarginsInPx);
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent event){
        view.finishAndOpenLoginActivity();
    }

    private TabLayout.OnTabSelectedListener getTabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                handleFab(tab.getPosition());
                TextView tabName = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.text_view);
                ConstraintLayout layout = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.layout);
                view.setTextViewColor(tabName, R.color.royal_purple);
                view.setLayoutBackground(layout, R.drawable.selected_tab_shape);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabName = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.text_view);
                ConstraintLayout layout = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.layout);
                view.setTextViewColor(tabName, R.color.white);
                view.setLayoutBackground(layout, R.drawable.unselected_tab_shape);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void handleFab(int position) {
        switch (position){
            case NEARBY_POSITION:
                view.slideUpFab(fabMarginsInPx);
                view.setFabIcon(R.drawable.ic_search_white_26dp);
                break;
            case MYPOSTS_POSITION:
                view.slideUpFab(fabMarginsInPx);
                view.setFabIcon(R.drawable.ic_plus_white_24dp);
                break;
            case SETTINGS_POSITION:
                view.slideDownFab(fabMarginsInPx);
        }
    }

    private List<Integer> getTabsLayoutsIds(){
        List<Integer> tabsLayoutIds = new ArrayList<>();
        tabsLayoutIds.add(R.layout.nearby_tab_layout);
        tabsLayoutIds.add(R.layout.my_posts_tab_layout);
        tabsLayoutIds.add(R.layout.settings_tab_layout);
        return tabsLayoutIds;
    }

    private List<Fragment> getViewPagerFragments(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NearbyFragment());
        fragments.add(new MyPostsFragment());
        fragments.add(new SettingsFragment());
        return fragments;
    }

}
