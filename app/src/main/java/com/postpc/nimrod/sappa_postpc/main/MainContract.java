package com.postpc.nimrod.sappa_postpc.main;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.models.MyPostModel;
import com.postpc.nimrod.sappa_postpc.models.NearbyPostModel;

import java.util.List;

public interface MainContract {

    interface View{

        void setUserNameTextView(String userName);

        void setViewPagerAndTabsLayout(List<Fragment> viewPagerFragments, List<Integer> tabsLayoutsIds, TabLayout.OnTabSelectedListener tabSelectedListener, int currentItemPosition);

        void setTextViewColor(TextView textView, int colorResourceId);

        void setLayoutBackground(ConstraintLayout layout, int drawableResourceId);

        void openNewPostFragment();

        void setFabIcon(int iconResourceId);

        void slideDownFab(float fabMarginsInPx);

        void slideUpFab(float fabMarginsInPx);

        void openNearbyPostFragment(NearbyPostModel nearbyPostModel);

        void openMyPostFragment(MyPostModel myPostModel);
    }

    interface Presenter{

        void init();

        void onFabClicked();

        void destroy();

        void setCurrentPage(int currentPagePosition);

        void onBackPressed();
    }
}
