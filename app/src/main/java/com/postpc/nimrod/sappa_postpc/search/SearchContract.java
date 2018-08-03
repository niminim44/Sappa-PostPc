package com.postpc.nimrod.sappa_postpc.search;

import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;

import java.util.List;

public interface SearchContract {

    interface View{

        void initRecyclerView(List<CategorySearchModel> categoriesInfo);
    }

    interface Presenter{

        void init();
    }

}
