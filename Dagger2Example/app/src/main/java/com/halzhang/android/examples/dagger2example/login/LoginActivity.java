package com.halzhang.android.examples.dagger2example.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.halzhang.android.examples.dagger2example.BaseActivity;
import com.halzhang.android.examples.dagger2example.ExampleApplication;
import com.halzhang.android.examples.dagger2example.R;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginFragment.OnLoginFragmentListener {

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.login_fragment);

        DaggerLoginComponent.builder().loginPresenterModule(new LoginPresenterModule(loginFragment))
                .userApiComponent(((ExampleApplication) getApplication()).getUserApiComponent())
                .build().inject(this);
    }

    @Override
    public void onResult(boolean success) {
        Toast.makeText(getApplicationContext(), success ? "登陆成功" : "登陆失败", Toast.LENGTH_SHORT).show();
    }
}
