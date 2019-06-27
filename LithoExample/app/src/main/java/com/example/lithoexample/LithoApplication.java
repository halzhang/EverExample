package com.example.lithoexample;

import android.app.Application;

import com.facebook.soloader.SoLoader;

public class LithoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, false);
    }
}
