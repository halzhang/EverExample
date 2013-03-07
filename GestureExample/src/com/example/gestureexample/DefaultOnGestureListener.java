/**
 * Copyright (C) 2013 HalZhang
 */

package com.example.gestureexample;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * GestureExample
 * <p>
 * </p>
 * 
 * @author <a href="http://weibo.com/halzhang">Hal</a>
 * @version Mar 6, 2013
 */
public class DefaultOnGestureListener implements OnGestureListener {

    private static final String LOG_TAG = DefaultOnGestureListener.class.getSimpleName();

    private OnGestureFlingListener mGestureFlingListener;

    private int mPointCount = 1;

    private View mView;

    private int mTouchSlop;

    public DefaultOnGestureListener(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setOnGestureFlingListener(OnGestureFlingListener listener) {
        mGestureFlingListener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e(LOG_TAG, "onDown:" + e.getPointerCount());
        mPointCount = 1;
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.e(LOG_TAG, "onShowPress:" + e.getPointerCount());

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e(LOG_TAG, "onSingleTapUp:" + e.getPointerCount());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // Log.e(LOG_TAG, "onScroll-E1-Point:" + e1.getPointerCount());
        // Log.e(LOG_TAG, "onScroll-E2-Point:" + e2.getPointerCount());
        // 这里获取单指或者双指
        int p1 = e1.getPointerCount();
        int p2 = e2.getPointerCount();
        if (mPointCount == 1 && (p1 == 1 && p2 == 1)) {
            // 单指
            mPointCount = 1;
        } else if (p1 > 1 || p2 > 1) {
            mPointCount = 2;
            // 双指
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.e(LOG_TAG, "onLongPress:" + e.getPointerCount());

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (mGestureFlingListener == null) {
            return false;
        }
        Log.e(LOG_TAG, "onFling-E1-Point:" + e1.getPointerCount());
        Log.e(LOG_TAG, "onFling-E2-Point:" + e2.getPointerCount());
        int x1 = (int) e1.getX();
        int y1 = (int) e1.getY();
        int x2 = (int) e2.getX();
        int y2 = (int) e2.getY();
        int distanceH = Math.abs(x2 - x1);
        int distanceV = Math.abs(y2 - y1);
        if (distanceH > distanceV && distanceH > mTouchSlop) {
            // 水平方向
            if (mPointCount == 1) {
                // 单指
                if (x2 - x1 > 0) {
                    return mGestureFlingListener.onSingleFlingRight(mView);

                } else {
                    return mGestureFlingListener.onSingleFlingLeft(mView);
                }
            } else if (mPointCount == 2) {
                // 双指
            }
        } else if (distanceV > distanceH && distanceV > mTouchSlop) {
            // 垂直方向
            if (mPointCount == 1) {
                // 单指
                if (y2 - y1 > 0) {
                    return mGestureFlingListener.onSingleFlingDown(mView);

                } else {
                    return mGestureFlingListener.onSingleFlingUp(mView);
                }
            } else if (mPointCount == 2) {
                // 双指
            }

        }

        return false;
    }

    /**
     * GestureExample
     * <p>
     * 手势监听
     * </p>
     * 
     * @author <a href="http://weibo.com/halzhang">Hal</a>
     * @version Mar 6, 2013
     */
    public interface OnGestureFlingListener {

        /**
         * 向左滑动
         * 
         * @param view
         */
        public boolean onSingleFlingLeft(View view);

        /**
         * 向右滑动
         * 
         * @param view
         */
        public boolean onSingleFlingRight(View view);

        /**
         * 向上
         * 
         * @param view
         */
        public boolean onSingleFlingUp(View view);

        /**
         * 向下
         * 
         * @param view
         */
        public boolean onSingleFlingDown(View view);

    }

}
