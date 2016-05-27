package com.halzhang.android.examples.dagger2example;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhanghanguo@yy.com on 2016/5/27.
 */
@Module
public class UserApiModule {

    @Singleton
    @Provides
    IUserApi provideUserApiImpl(Context context) {
        return new UserApiImpl(context);
    }

}
