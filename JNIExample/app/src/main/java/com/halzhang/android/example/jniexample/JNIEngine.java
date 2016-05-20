package com.halzhang.android.example.jniexample;

/**
 * Created by zhanghanguo@yy.com on 2016/5/20.
 */
public class JNIEngine {

    static {
        System.loadLibrary("JNIEngine");
    }

    public native boolean init(Object context);

}
