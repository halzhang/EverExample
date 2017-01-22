package com.halzhang.android.example.jniexample;

import android.os.Bundle;

/**
 * Created by Hal on 2016/5/20.
 */
public class JNIEngine {

    static {
        System.loadLibrary("JNIEngine");
    }

    public native boolean init(Object context);

    public native boolean testLocalRef();

    public native String createString();

    public native Bundle getData();

}
