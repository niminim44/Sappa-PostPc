package com.postpc.nimrod.sappa_postpc.core;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.realm.Realm;

public class SappaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
