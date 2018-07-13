package com.postpc.nimrod.sappa_postpc.preferences;

import android.content.SharedPreferences;

public class Preferences {

    public static final String PREFS_NAME = "sappa-preferences";

    private static final String USER_ID = "user-id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String DEFAULT_USER_NAME = "default";
    private static final String DEFAULT_USER_ID = "12345678";
    private final SharedPreferences sharedPreferences;

    public Preferences(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }


    public void saveUserInfo(String userId, String name, String email) {
        saveUserId(userId);
        saveName(name);
        saveEmail(email);
    }

    public String getUserName(){
        return sharedPreferences.getString(NAME, DEFAULT_USER_NAME);
    }

    public String getUserId(){
        return sharedPreferences.getString(USER_ID, DEFAULT_USER_ID);
    }

    private void saveName(String name) {
        sharedPreferences.edit()
                .putString(NAME, name)
                .apply();
    }

    private void saveUserId(String userId) {
        sharedPreferences.edit()
                .putString(USER_ID, userId)
                .apply();
    }

    private void saveEmail(String email) {
        sharedPreferences.edit()
                .putString(EMAIL, email)
                .apply();
    }
}
