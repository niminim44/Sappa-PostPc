package com.postpc.nimrod.sappa_postpc.preferences;

import android.content.SharedPreferences;

import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;

import java.util.ArrayList;
import java.util.List;

public class Preferences {

    public static final String PREFS_NAME = "sappa-preferences";

    private static final String USER_ID = "user-id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String DEFAULT_USER_NAME = "default";
    private static final String DEFAULT_USER_ID = "12345678";
    private static final String RANGE = "range";
    private static final int DEAFAULT_RANGE_VALUE = 10;
    private static final String DEFAULT_EMAIL = "no email info";
    private static final String ELECTRONICS = "Electronics";
    private static final String FURNITURE = "Furniture";
    private static final String BOOKS = "Books";
    private static final String CLOTHING = "Clohting";
    private static final String SPORTS = "Sports & Hobbies";
    private static final String CHILDREN = "Children & Infants";
    private static final String OTHER = "Other";
    private static final String FREE_TEXT_SEARCH = "free text search";
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

    public void saveCurrentRangeFilter(Integer range){
        sharedPreferences.edit()
                .putInt(RANGE, range)
                .apply();
    }

    public void saveCurrentCategoryFilter(List<CategorySearchModel> categories){
        for(CategorySearchModel category: categories){
            sharedPreferences.edit()
                    .putBoolean(category.getName(), category.isSelected())
                    .apply();
        }
    }

    public int getCurrentRangeFilter() {
        return sharedPreferences.getInt(RANGE, DEAFAULT_RANGE_VALUE);
    }

    public List<CategorySearchModel> getCurrentCategoryFilter() {
        List<CategorySearchModel> categoriesFilter = new ArrayList<>();
        categoriesFilter.add(new CategorySearchModel(ELECTRONICS, sharedPreferences.getBoolean(ELECTRONICS, true)));
        categoriesFilter.add(new CategorySearchModel(FURNITURE, sharedPreferences.getBoolean(FURNITURE, true)));
        categoriesFilter.add(new CategorySearchModel(BOOKS, sharedPreferences.getBoolean(BOOKS, true)));
        categoriesFilter.add(new CategorySearchModel(CLOTHING, sharedPreferences.getBoolean(CLOTHING, true)));
        categoriesFilter.add(new CategorySearchModel(SPORTS, sharedPreferences.getBoolean(SPORTS, true)));
        categoriesFilter.add(new CategorySearchModel(CHILDREN, sharedPreferences.getBoolean(CHILDREN, true)));
        categoriesFilter.add(new CategorySearchModel(OTHER, sharedPreferences.getBoolean(OTHER, true)));
        return categoriesFilter;
    }

    public String getUserEmail() {
        return sharedPreferences.getString(EMAIL, DEFAULT_EMAIL);
    }

    public void clearAllFields() {
        saveName(DEFAULT_USER_NAME);
        saveEmail(DEFAULT_EMAIL);
        saveUserId(DEFAULT_USER_ID);
        clearCategoriesFilter();
        saveFreeTextSearch("");
        saveCurrentRangeFilter(DEAFAULT_RANGE_VALUE);
    }

    private void clearCategoriesFilter() {
        List<CategorySearchModel> categoriesFilter = new ArrayList<>();
        categoriesFilter.add(new CategorySearchModel(ELECTRONICS, true));
        categoriesFilter.add(new CategorySearchModel(FURNITURE, true));
        categoriesFilter.add(new CategorySearchModel(BOOKS, true));
        categoriesFilter.add(new CategorySearchModel(CLOTHING, true));
        categoriesFilter.add(new CategorySearchModel(SPORTS, true));
        categoriesFilter.add(new CategorySearchModel(CHILDREN, true));
        categoriesFilter.add(new CategorySearchModel(OTHER, true));
        saveCurrentCategoryFilter(categoriesFilter);
    }

    public void refreshSearchSettings() {
        clearCategoriesFilter();
    }

    public void saveFreeTextSearch(String freeText) {
        sharedPreferences.edit()
                .putString(FREE_TEXT_SEARCH, freeText)
                .apply();
    }

    public String getFreeTextFilter() {
        return sharedPreferences.getString(FREE_TEXT_SEARCH, "");
    }
}
