package com.halzhang.android.examples.dagger2example;

import android.content.Context;

import com.halzhang.android.examples.dagger2example.entity.User;

import javax.inject.Singleton;

import rx.Observable;

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
    public Observable<User> login(String username, String password) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ("admin".equals(username) && "admin".equals(password)) {
            User user = new User();
            user.mAge = 18;
            user.mFirstName = "First";
            user.mLastName = "Last";
            user.mUsername = "foo";
            user.mUid = 123123;
            return Observable.just(user);
        } else {
            return Observable.just(null);
        }

    }
}
