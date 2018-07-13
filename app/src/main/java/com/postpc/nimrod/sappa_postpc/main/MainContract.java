package com.postpc.nimrod.sappa_postpc.main;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.List;

public interface MainContract {

    interface View{


        void showFab();

        void setUserNameTextView(String userName);

        void setViewPagerAndTabsLayout(List<Fragment> viewPagerFragments, List<Integer> tabsLayoutsIds, TabLayout.OnTabSelectedListener tabSelectedListener, int currentItemPosition);

        void setTextViewColor(TextView textView, int colorResourceId);

        void setLayoutBackground(ConstraintLayout layout, int drawableResourceId);

        void openNewPostFragment();

        void hideFab();

        void setFabIcon(int iconResourceId);

        void slideDownFab(float fabMarginsInPx);

        void slideUpFab(float fabMarginsInPx);
    }

    interface Presenter{

        void init();

        void onFabClicked();
    }
}
