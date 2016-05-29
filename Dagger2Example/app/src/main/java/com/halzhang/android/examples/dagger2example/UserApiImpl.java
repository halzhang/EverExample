package com.halzhang.android.examples.dagger2example;

import android.content.Context;
import android.util.Log;

import com.halzhang.android.examples.dagger2example.entity.User;

import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

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
    public Observable<User> login(final String username, final String password) {

        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                if ("admin".equals(username) && "admin".equals(password)) {
                    User user = new User();
                    user.mAge = 18;
                    user.mFirstName = "First";
                    user.mLastName = "Last";
                    user.mUsername = "foo";
                    user.mUid = 123123;
                    subscriber.onNext(user);
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }
        });

    }
}
