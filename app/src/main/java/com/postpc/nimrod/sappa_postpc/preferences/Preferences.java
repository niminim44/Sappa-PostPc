package com.postpc.nimrod.sappa_postpc.preferences;

import android.content.SharedPreferences;

public class Preferences {

    public static final String PREFS_NAME = "sappa-preferences";

    private static final String USER_ID = "user-id";
    private static final String FIRST_NAME = "first-name";
    private static final String LAST_NAME = "last-name";
    private static final String EMAIL = "email";
    private final SharedPreferences sharedPreferences;

    public Preferences(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }


    public void saveUserInfo(String userId, String firstName, String lastName, String email) {
        saveUserId(userId);
        saveFirstName(firstName);
        saveLastName(lastName);
        saveEmail(email);
    }

    private void saveUserId(String userId) {
        sharedPreferences.edit()
                .putString(USER_ID, userId)
                .apply();
    }


    private void saveFirstName(String firstName) {
        sharedPreferences.edit()
                .putString(FIRST_NAME, firstName)
                .apply();
    }


    private void saveLastName(String lastName) {
        sharedPreferences.edit()
                .putString(LAST_NAME, lastName)
                .apply();
    }


    private void saveEmail(String email) {
        sharedPreferences.edit()
                .putString(EMAIL, email)
                .apply();
    }
}
