package com.postpc.nimrod.sappa_postpc.search;

class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;

    SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {

    }
}
