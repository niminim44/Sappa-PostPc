package com.postpc.nimrod.sappa_postpc.core;

import android.app.Application;

import io.realm.Realm;

public class SappaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
