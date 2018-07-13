package com.postpc.nimrod.sappa_postpc.main.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class UiUtils {

    public UiUtils(){}

    public float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
