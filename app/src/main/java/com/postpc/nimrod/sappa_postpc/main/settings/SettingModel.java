package com.postpc.nimrod.sappa_postpc.main.settings;

import android.animation.TimeInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SettingModel{

    private final int titleResourceId;
    private final TimeInterpolator interpolator;
    private final int colorResourceId;

    SettingModel(int titleResourceId, int colorResourceId, TimeInterpolator interpolator) {
        this.titleResourceId = titleResourceId;
        this.colorResourceId = colorResourceId;
        this.interpolator = interpolator;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public int getColorResourceId() {
        return colorResourceId;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }
}
