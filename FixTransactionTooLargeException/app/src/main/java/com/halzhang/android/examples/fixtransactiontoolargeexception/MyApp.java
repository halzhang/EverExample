package com.halzhang.android.examples.fixtransactiontoolargeexception;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.state.StateSaver;
import com.gu.toolargetool.TooLargeTool;
import com.livefront.bridge.Bridge;
import com.livefront.bridge.SavedStateHandler;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TooLargeTool.startLogging(this);
        Bridge.initialize(this, new SavedStateHandler() {
            @Override
            public void saveInstanceState(@NonNull Object target, @NonNull Bundle state) {
                StateSaver.saveInstanceState(target, state);
            }

            @Override
            public void restoreInstanceState(@NonNull Object target, @Nullable Bundle state) {
                StateSaver.restoreInstanceState(target, state);
            }
        });
    }
}
