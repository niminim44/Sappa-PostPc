package com.postpc.nimrod.sappa_postpc.login;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

class LoginPresenter implements LoginContract.Presenter {

    private static final String EMAIL = "email";
    private static final String ID = "id";
    private static final String NAME = "name";


    private static final String FACEBOOK_PROFILE_FIELDS = "id,name,email";
    private static final String FIELDS = "fields";

    private final LoginContract.View view;
    private final CallbackManager facebookCallbackManager;
    private final Preferences preferences;

    LoginPresenter(LoginContract.View view, CallbackManager facebookCallbackManager, Preferences preferences) {
        this.view = view;
        this.facebookCallbackManager = facebookCallbackManager;
        this.preferences = preferences;
    }

    @Override
    public void init() {
        view.hideLoginViews();
        view.showProgressBar();
        if(isLoggedIn()){
            view.startMainActivityAndFinish();
        }
        else{
            view.hideProgressBar();
            view.showLoginViews();
            view.initializeFacebookLoginButton(facebookCallbackManager, getFacebookCallback());
        }
    }

    //TODO - I return 'true' to skip the log in screen.
    private boolean isLoggedIn() {
        return true;
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        return accessToken != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void onLoginSuccess(LoginResult loginResult) {
        requestUserProperties(loginResult.getAccessToken());
    }

    private void requestUserProperties(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    try {
                        saveUserProperties(object);
                        moveToMainActivity();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, FACEBOOK_PROFILE_FIELDS);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private FacebookCallback<LoginResult> getFacebookCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onLoginSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                onLoginCancel();
            }

            @Override
            public void onError(FacebookException error) {
                onLoginError(error);
            }
        };
    }

    private void moveToMainActivity() {
        view.startMainActivityAndFinish();
    }

    private void saveUserProperties(JSONObject properties) throws JSONException {
        preferences.saveUserInfo(
                properties.getString(ID),
                properties.getString(NAME),
                properties.getString(EMAIL));
    }

    private void onLoginCancel() {
        view.toastError(R.string.login_cancel_message);
    }

    private void onLoginError(FacebookException error) {
        view.toastError(error.getMessage());
    }


}
