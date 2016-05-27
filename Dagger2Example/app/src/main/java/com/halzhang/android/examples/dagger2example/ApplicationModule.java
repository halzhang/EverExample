package com.halzhang.android.examples.dagger2example;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * 应用上下文，用来提供注入上下文
 * Created by zhanghanguo@yy.com on 2016/5/27.
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
