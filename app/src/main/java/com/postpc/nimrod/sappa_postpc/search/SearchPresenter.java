package com.postpc.nimrod.sappa_postpc.search;

import com.postpc.nimrod.sappa_postpc.main.events.RefreshDataEvent;
import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private Preferences preferences;
    private EventBus eventBus;

    SearchPresenter(SearchContract.View view, Preferences preferences, EventBus eventBus) {
        this.view = view;
        this.preferences = preferences;
        this.eventBus = eventBus;
    }

    @Override
    public void init() {
        view.initSearchEditText(preferences.getFreeTextFilter());
        view.initRecyclerView(getCategoriesInfo());
    }

    @Override
    public void onSearchClicked() {
        String freeSearchText = view.getSearchEditTextInput();
        List<CategorySearchModel> searchCategories = view.getCategoriesToSearch();
        preferences.saveCurrentCategoryFilter(searchCategories);
        preferences.saveFreeTextSearch(freeSearchText);
        eventBus.post(new RefreshDataEvent());
        view.callOnBackPressed();
    }

    private List<CategorySearchModel> getCategoriesInfo() {
        return preferences.getCurrentCategoryFilter();
    }
}
