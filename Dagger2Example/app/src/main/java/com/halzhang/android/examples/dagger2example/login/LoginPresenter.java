package com.halzhang.android.examples.dagger2example.login;

import android.support.annotation.NonNull;

import com.halzhang.android.examples.dagger2example.IUserApi;
import com.halzhang.android.examples.dagger2example.entity.User;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Login Presenter
 * Created by Hal on 16/5/28.
 */
public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private final IUserApi mUserApi;

    @NonNull
    private final LoginContract.View mLoginView;

    @Inject
    LoginPresenter(@NonNull IUserApi userApi, @NonNull LoginContract.View loginView) {
        mUserApi = userApi;
        mLoginView = loginView;
    }

    /**
     * 注入调用，设置 Presenter
     */
    @Inject
    void setupListeners() {
        mLoginView.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        mUserApi.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.onLoginResult(0, e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        if (!mLoginView.isActive()) {
                            return;
                        }
                        if (user != null) {
                            mLoginView.onLoginResult(1, "success");
                        } else {
                            mLoginView.onLoginResult(0, "failure");
                        }
                    }
                });
    }

    @Override
    public void start() {
        //noop
    }
}
