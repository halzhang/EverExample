package com.halzhang.android.examples.activityliftcycle;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public abstract class AbsBaseActivity extends Activity {

    private String mLogTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogTag = getActivityName();
        Log.e(mLogTag, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(mLogTag, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(mLogTag, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(mLogTag, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(mLogTag, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(mLogTag, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(mLogTag, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(mLogTag, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(mLogTag, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(mLogTag, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    public abstract String getActivityName();

}
