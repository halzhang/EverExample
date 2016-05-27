package com.halzhang.android.examples.dagger2example;

import android.content.Context;

import javax.inject.Singleton;

/**
 * Created by zhanghanguo@yy.com on 2016/5/27.
 */
@Singleton
public class UserApiImpl implements IUserApi {

    private Context mContext;

    public UserApiImpl(Context context) {
        mContext = context;
    }

    @Override
    public String getUserInfo() {
        return null;
    }
}
