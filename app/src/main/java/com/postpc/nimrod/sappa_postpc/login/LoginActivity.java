package com.postpc.nimrod.sappa_postpc.login;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.MainActivity;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    private static final String EMAIL = "email";

    @BindView(R.id.login_button)
    LoginButton facebookLoginButton;

    @BindViews({R.id.inner_view, R.id.app_explanation_text_view, R.id.app_login_with_text_view, R.id.login_button})
    List<View> loginViews;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        forceLtr();
        presenter = new LoginPresenter(this, CallbackManager.Factory.create(), new Preferences(getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)));
        presenter.init();
    }

    private void forceLtr() {
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("en"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    @Override
    public void initializeFacebookLoginButton(CallbackManager facebookCallbackManager, FacebookCallback<LoginResult> facebookCallback) {
        facebookLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        facebookLoginButton.registerCallback(facebookCallbackManager, facebookCallback);
    }

    @Override
    public void toastError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivityAndFinish() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void toastError(int stringResourceId) {
        Toast.makeText(this, getResources().getString(stringResourceId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideLoginViews() {
        for(View view: loginViews){
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoginViews() {
        for(View view: loginViews){
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
