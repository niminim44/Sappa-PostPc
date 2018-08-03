package com.postpc.nimrod.sappa_postpc.main.settings;

public interface SettingsContract {

    interface View{

        void setSettingsTitle(String userName, int prefixResourceId);

        void initUserInfoLayout(boolean expandedState);

        void initUserInfo(String userName, String userEmail);

        void initDistanceSettingsLayout(boolean distanceExpandedState);

        void initCurrentDistance(Integer initialProgress, String distanceText);

        void setDistanceTextView(String distance);

        void initAboutLayout(boolean aboutExpandedState);
    }

    interface Presenter{

        void init();

        void onUserInfoPreOpen();

        void onUserInfoPreClose();

        void onLogoutClicked();

        void onDistancePreOpen();

        void onDistancePreClose();

        void onDistanceChanged(int currentDistance, boolean fromUser);

        void onAboutPreOpen();

        void onAboutPreClose();
    }

}