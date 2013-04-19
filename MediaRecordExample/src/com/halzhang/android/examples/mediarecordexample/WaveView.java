/*
 * Copyright (C) 2013 HalZhang.
 *
 * http://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.halzhang.android.examples.mediarecordexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * BaiduMapTools
 * <p>
 * 波形
 * </p>
 * 
 * @author <a href="http://weibo.com/halzhang">Hal</a>
 * @version Apr 15, 2013
 */
public class WaveView extends View {

    private int mWidth;

    private int mHeight;

    private int mMaxAmplitude;// 波形最大值

    private Paint mFirstPaint;

    private Paint mSecondPaint;

    private RectF[] mOvalRectFs;

    private int mOnCount;

    float mStep = 0f;// 步长

    private static final int COUNT = 20;

    private float mRadius = 5.0f;

    public WaveView(Context context) {
        super(context);
        mFirstPaint = new Paint();
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setStyle(Paint.Style.STROKE);
        mFirstPaint.setColor(Color.RED);
        mFirstPaint.setStrokeWidth(5);

        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mSecondPaint.setColor(Color.GREEN);
        mSecondPaint.setStrokeWidth(5);
    }

    private float mStartAngle;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
        mOvalRectFs = new RectF[COUNT];
        mStep = (mWidth * 0.5f - mRadius) * 0.05f;
        float ox = mWidth * 0.5f;
        float oy = mHeight * 0.5f;
        float x = 0f;
        float y = 0f;
        for (int i = 0; i < COUNT; i++) {
            x = mRadius + i * mStep;
            y = (((x - ox) * oy) / ox) + ox;
            float left = ox - x;
            float top = oy - y;
            float right = ox + x;
            float bottom = oy + y;
            mOvalRectFs[i] = new RectF(left, top, right, bottom);
        }

        mStartAngle = (float) ((Math.atan(Math.abs(oy / ox)) * 180) / Math.PI);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMaxAmplitude > 0) {
            canvas.drawCircle(mWidth * 0.5f, mHeight * 0.5f, mRadius, mSecondPaint);
        } else {
            canvas.drawCircle(mWidth * 0.5f, mHeight * 0.5f, mRadius, mFirstPaint);
        }
        for (int i = 0; i < COUNT; i++) {
            if (i > mOnCount) {
                canvas.drawArc(mOvalRectFs[i], mStartAngle, -(mStartAngle * 2), false, mFirstPaint);
                canvas.drawArc(mOvalRectFs[i], 180 + mStartAngle, -(mStartAngle * 2), false,
                        mFirstPaint);
            } else {
                canvas.drawArc(mOvalRectFs[i], mStartAngle, -(mStartAngle * 2), false, mSecondPaint);
                canvas.drawArc(mOvalRectFs[i], 180 + mStartAngle, -(mStartAngle * 2), false,
                        mSecondPaint);

            }
        }
    }

    public void setMaxAmplitude(int amplitude) {
        mMaxAmplitude = amplitude;
        if (mMaxAmplitude > 0) {
            float onWidth = (mMaxAmplitude * mWidth) / 10000;
            mOnCount = (int) ((onWidth - mRadius) / mStep);
            if (mOnCount > COUNT) {
                mOnCount = COUNT;
            }
        } else {
            mOnCount = 0;
        }
        postInvalidate();
    }

}
