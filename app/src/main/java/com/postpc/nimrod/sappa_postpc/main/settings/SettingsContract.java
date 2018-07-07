package com.postpc.nimrod.sappa_postpc.main.settings;

import java.util.List;

public interface SettingsContract {

    interface View{

        void initRecyclerView(List<SettingModel> settingItems);

        void setSettingsTitle(String userName, int prefixResourceId);
    }

    interface Presenter{

        void init();
    }

}