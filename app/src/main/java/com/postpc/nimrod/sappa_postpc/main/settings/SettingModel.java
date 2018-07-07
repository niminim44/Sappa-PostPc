package com.postpc.nimrod.sappa_postpc.main.settings;

import android.animation.TimeInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SettingModel{

    private final String description;
    private final int colorId1;
    private final int colorId2;
    private final TimeInterpolator interpolator;

    public SettingModel(String description, int colorId1, int colorId2, TimeInterpolator interpolator) {
        this.description = description;
        this.colorId1 = colorId1;
        this.colorId2 = colorId2;
        this.interpolator = interpolator;
    }

    public String getDescription() {
        return description;
    }

    public int getColorId1() {
        return colorId1;
    }

    public int getColorId2() {
        return colorId2;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }
}
