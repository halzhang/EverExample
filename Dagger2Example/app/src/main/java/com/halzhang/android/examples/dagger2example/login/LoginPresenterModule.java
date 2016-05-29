package com.halzhang.android.examples.dagger2example.login;

import dagger.Module;
import dagger.Provides;

/**
 * 提供 {@link LoginPresenter} 创建所需参数
 * Created by Hal on 16/5/29.
 */
@Module
public class LoginPresenterModule {

    private final LoginContract.View mView;

    public LoginPresenterModule(LoginContract.View view) {
        mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return mView;
    }


}
