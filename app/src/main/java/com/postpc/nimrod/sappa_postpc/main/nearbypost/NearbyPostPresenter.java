package com.postpc.nimrod.sappa_postpc.main.nearbypost;

class NearbyPostPresenter implements NearbyPostContract.Presenter{


    private NearbyPostContract.View view;

    NearbyPostPresenter(NearbyPostContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {

    }

    @Override
    public void backButtonClicked() {
        view.callOnBackPressed();
    }
}
