package com.postpc.nimrod.sappa_postpc.search;

import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;

import java.util.ArrayList;
import java.util.List;

class SearchPresenter implements SearchContract.Presenter {

    private static final String ELECTRONICS = "Electronics";
    private static final String FURNITURE = "Furniture";
    private static final String BOOKS = "Books";
    private static final String CLOTHING = "Clothing";
    private static final String SPORTS = "Sports & Hobbies";
    private static final String CHILDREN = "Children & Infants";
    private static final String OTHER = "Other";

    private SearchContract.View view;

    SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        view.initRecyclerView(getCategoriesInfo());
    }

    private List<CategorySearchModel> getCategoriesInfo() {
        List<CategorySearchModel> categories = new ArrayList<>();
        categories.add(new CategorySearchModel(ELECTRONICS, true));
        categories.add(new CategorySearchModel(FURNITURE, true));
        categories.add(new CategorySearchModel(BOOKS, true));
        categories.add(new CategorySearchModel(CLOTHING, true));
        categories.add(new CategorySearchModel(SPORTS, true));
        categories.add(new CategorySearchModel(CHILDREN, true));
        categories.add(new CategorySearchModel(OTHER, true));
        return categories;
    }
}
