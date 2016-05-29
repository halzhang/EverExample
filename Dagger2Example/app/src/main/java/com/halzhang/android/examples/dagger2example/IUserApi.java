package com.halzhang.android.examples.dagger2example;

import com.halzhang.android.examples.dagger2example.entity.User;

import rx.Observable;

/**
 * Created by zhanghanguo@yy.com on 2016/5/27.
 */
public interface IUserApi {


    Observable<User> login(String username, String password);


}
