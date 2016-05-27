package com.halzhang.android.examples.dagger2example;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhanghanguo@yy.com on 2016/5/27.
 */
@Singleton
@Component(modules = {UserApiModule.class, ApplicationModule.class})
public interface IUserApiComponent {

    IUserApi getUserApi();

}
