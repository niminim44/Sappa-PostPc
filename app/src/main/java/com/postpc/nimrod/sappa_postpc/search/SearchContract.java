package com.postpc.nimrod.sappa_postpc.search;

import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;

import java.util.List;

public interface SearchContract {

    interface View{

        void initRecyclerView(List<CategorySearchModel> categoriesInfo);

        String getSearchEditTextInput();

        List<CategorySearchModel> getCategoriesToSearch();

        void callOnBackPressed();

        void initSearchEditText(String freeTextFilter);
    }

    interface Presenter{

        void init();

        void onSearchClicked();
    }

}
