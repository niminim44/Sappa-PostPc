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
                "0 ACCELERATE_DECELERATE_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)));
        data.add(new SettingModel(
                "1 ACCELERATE_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.ACCELERATE_INTERPOLATOR)));
        data.add(new SettingModel(
                "2 BOUNCE_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.BOUNCE_INTERPOLATOR)));
        data.add(new SettingModel(
                "3 DECELERATE_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.DECELERATE_INTERPOLATOR)));
        data.add(new SettingModel(
                "4 FAST_OUT_LINEAR_IN_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR)));
        data.add(new SettingModel(
                "5 FAST_OUT_SLOW_IN_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new SettingModel(
                "6 LINEAR_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR)));
        data.add(new SettingModel(
                "7 LINEAR_OUT_SLOW_IN_INTERPOLATOR",
                R.color.persianBlue,
                R.color.orchid,
                Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)));
        return data;
    }
}
