package com.postpc.nimrod.sappa_postpc.main;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.myposts.MyPostsFragment;
import com.postpc.nimrod.sappa_postpc.main.nearby.NearbyFragment;
import com.postpc.nimrod.sappa_postpc.main.settings.SettingsFragment;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class MainPresenter implements MainContract.Presenter{

    private static final int INITIAL_TAB_LAYOUT_POSITION = 0;
    private final Preferences preferences;
    private MainContract.View view;

    MainPresenter(MainContract.View view, Preferences preferences) {
        this.view = view;
        this.preferences = preferences;
    }

    @Override
    public void init() {
        view.setUserNameTextView(preferences.getUserName());
        view.setViewPagerAndTabsLayout(getViewPagerFragments(), getTabsLayoutsIds(),
                getTabSelectedListener(), INITIAL_TAB_LAYOUT_POSITION);
    }

    @Override
    public void onFabClicked() {
        view.openNewPostFragment();
    }

    private TabLayout.OnTabSelectedListener getTabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
