package com.postpc.nimrod.sappa_postpc.login;

import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

public interface LoginContract {

    interface View{


        void initializeFacebookLoginButton(CallbackManager facebookCallbackManager, FacebookCallback<LoginResult> facebookCallback);

        void toastError(String errorMessage);

        void startMainActivityAndFinish();

        void toastError(int stringResourceId);

        void showProgressBar();

        void hideProgressBar();

        void hideLoginViews();

        void showLoginViews();
    }

    interface Presenter{

        void init();

        void onActivityResult(int requestCode, int resultCode, Intent data);

    }
}
