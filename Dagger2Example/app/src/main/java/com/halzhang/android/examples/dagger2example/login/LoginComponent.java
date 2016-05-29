package com.halzhang.android.examples.dagger2example.login;

import com.halzhang.android.examples.dagger2example.UserApiComponent;
import com.halzhang.android.examples.dagger2example.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by Hal on 16/5/29.
 */
@FragmentScoped
@Component(dependencies = UserApiComponent.class, modules = LoginPresenterModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}
