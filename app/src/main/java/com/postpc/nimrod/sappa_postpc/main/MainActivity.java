package com.postpc.nimrod.sappa_postpc.main;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.newpost.NewPostFragment;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    @BindView(R.id.user_name_text_view)
    TextView userNameTextView;

    @BindView(R.id.tabs_layout)
    TabLayout tabsLayout;

    @BindView(R.id.main_view_pager)
    ViewPager viewPager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.container)
    FrameLayout container;

    private MainContract.Presenter presenter;
    private ViewPagerAdapter fragmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this, new Preferences(getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)));
        presenter.init();
    }

    @Override
    public void setUserNameTextView(String userName) {
        userNameTextView.setText(userName);
    }

    @Override
    public void setViewPagerAndTabsLayout(List<Fragment> viewPagerFragments, List<Integer> tabsLayoutsIds,
                                          TabLayout.OnTabSelectedListener tabSelectedListener, int currentItemPosition) {
        setupViewPager(viewPagerFragments);
        setupTabsLayout(tabsLayoutsIds, tabSelectedListener);
        viewPager.setCurrentItem(currentItemPosition);
    }

    @Override
    public void setTextViewColor(TextView textView, int colorResourceId) {
        textView.setTextColor(getResources().getColor(colorResourceId));
    }

    @Override
    public void setLayoutBackground(ConstraintLayout layout, int drawableResourceId) {
        layout.setBackground(getResources().getDrawable(drawableResourceId));
    }

    @Override
    public void openNewPostFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new NewPostFragment())
                .addToBackStack("new_post")
                .commit();
        container.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fab)
    public void onFabClicked(){
        presenter.onFabClicked();
    }

    private void setupTabsLayout(List<Integer> tabsLayoutsIds, TabLayout.OnTabSelectedListener tabSelectedListener) {
        tabsLayout.setupWithViewPager(viewPager);
        tabsLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.transparent));
        tabsLayout.setSelectedTabIndicatorHeight(1);
        tabsLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.royal_purple));
        for(int i = 0; i < tabsLayoutsIds.size(); i++){
            Objects.requireNonNull(tabsLayout.getTabAt(i)).setCustomView(tabsLayoutsIds.get(i));
        }
        tabsLayout.addOnTabSelectedListener(tabSelectedListener);
    }

    private void setupViewPager(List<Fragment> viewPagerFragments) {
        fragmentsAdapter = new ViewPagerAdapter(getSupportFragmentManager(), viewPagerFragments);
        viewPager.setAdapter(fragmentsAdapter);
    }
}
