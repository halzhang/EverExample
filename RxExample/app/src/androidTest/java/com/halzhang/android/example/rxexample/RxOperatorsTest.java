package com.halzhang.android.example.rxexample;

import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Rx操作测试
 * Created by zhanghanguo@yy.com on 2015/12/16.
 */
@RunWith(AndroidJUnit4.class)
public class RxOperatorsTest extends ActivityInstrumentationTestCase2 {

    public RxOperatorsTest() {
        super(TestActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    static class DrawableInfo {
        public int mResId;
        public Drawable mDrawable;
    }

    @Test
    public void textConcat() {
        assertTrue(getInstrumentation().getContext() != null);
        final int resId1 = 0;
        final int resId2 = 0;
        final int resId3 = 0;


        Observable<DrawableInfo> drawableObservable1 = Observable.create(new Observable.OnSubscribe<DrawableInfo>() {
            @Override
            public void call(Subscriber<? super DrawableInfo> subscriber) {
                if (resId1 > 0) {
                    Drawable drawable = getInstrumentation().getContext().getResources().getDrawable(resId1);
                    if (drawable != null) {
                        DrawableInfo drawableInfo = new DrawableInfo();
                        drawableInfo.mResId = resId1;
                        drawableInfo.mDrawable = drawable;
                        subscriber.onNext(drawableInfo);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception());
                    }
                } else {
                    subscriber.onError(new Exception());
                }
            }
        });
        Observable<DrawableInfo> drawableObservable2 = Observable.create(new Observable.OnSubscribe<DrawableInfo>() {
            @Override
            public void call(Subscriber<? super DrawableInfo> subscriber) {
                if (resId2 > 0) {
                    Drawable drawable = getInstrumentation().getContext().getResources().getDrawable(resId2);
                    if (drawable != null) {
                        DrawableInfo drawableInfo = new DrawableInfo();
                        drawableInfo.mResId = resId2;
                        drawableInfo.mDrawable = drawable;
                        subscriber.onNext(drawableInfo);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception());
                    }
                } else {
                    subscriber.onError(new Exception());
                }
            }
        });
        Observable<DrawableInfo> drawableObservable3 = Observable.create(new Observable.OnSubscribe<DrawableInfo>() {
            @Override
            public void call(Subscriber<? super DrawableInfo> subscriber) {
                if (resId3 > 0) {
                    Drawable drawable = getInstrumentation().getContext().getResources().getDrawable(resId3);
                    if (drawable != null) {
                        DrawableInfo drawableInfo = new DrawableInfo();
                        drawableInfo.mResId = resId3;
                        drawableInfo.mDrawable = drawable;
                        subscriber.onNext(drawableInfo);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception());
                    }
                } else {
                    subscriber.onError(new Exception());
                }
            }
        });

        Observable.concat(drawableObservable1, drawableObservable2, drawableObservable3)
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DrawableInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RX", e.getMessage(), e);
                    }

                    @Override
                    public void onNext(DrawableInfo drawable) {
                        Toast.makeText(getInstrumentation().getContext(), String.valueOf(drawable.mResId), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
