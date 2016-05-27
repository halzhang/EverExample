package com.halzhang.android.examples.dagger2example;

import android.app.Application;

/**
 * Created by zhanghanguo@yy.com on 2016/5/27.
 */
public class ExampleApplication extends Application {

    private IUserApiComponent mUserApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mUserApiComponent = DaggerIUserApiComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .userApiModule(new UserApiModule()).build();
    }

    public IUserApiComponent getUserApiComponent() {
        return mUserApiComponent;
    }
}
