package com.halzhang.android.examples.dagger2example.login;

import com.halzhang.android.examples.dagger2example.BasePresenter;
import com.halzhang.android.examples.dagger2example.BaseView;

/**
 * Created by Hal on 16/5/28.
 */
public interface LoginContract {

    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }


    interface View extends BaseView<Presenter> {

        void onLoginResult(int code, String message);

        boolean isActive();
    }


}
