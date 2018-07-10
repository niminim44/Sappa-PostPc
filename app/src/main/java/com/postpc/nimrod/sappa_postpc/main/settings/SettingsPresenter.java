package com.postpc.nimrod.sappa_postpc.main.settings;

import com.github.aakira.expandablelayout.Utils;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

class SettingsPresenter implements SettingsContract.Presenter{

    private SettingsContract.View view;
    private Preferences preferences;

    SettingsPresenter(SettingsContract.View view, Preferences preferences) {
        this.view = view;
        this.preferences = preferences;
    }


    @Override
    public void init() {
        view.setSettingsTitle(preferences.getUserName(), R.string.hi);
        List<SettingModel> settingItems = getSettingItems();
        view.initRecyclerView(settingItems);
    }

    private List<SettingModel> getSettingItems() {
        final List<SettingModel> data = new ArrayList<>();
        data.add(new SettingModel(
                R.string.user_info,
                R.color.dodger_blue,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new SettingModel(
                R.string.notification_settings,
                R.color.burnt_sienna,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new SettingModel(
                R.string.about,
                R.color.jungle_green,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
       return data;
    }
}
