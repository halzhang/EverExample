/*
 * Copyright (C) 2013 HalZhang.
 *
 * http://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.halzhang.android.examples.revolvegesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * RevolveGesture
 * <p>
 * 旋转进度
 * </p>
 * 
 * @author <a href="http://weibo.com/halzhang">Hal</a>
 * @version Apr 8, 2013
 */
public class RevolveGestureView extends View {

    private static final String LOG_TAG = RevolveGestureView.class.getSimpleName();

    private Bitmap mBitmap;

    private int mMaxWidth = 0;

    private float mCenterX = 0;

    private float mCenterY = 0;

    private int width;

    private int height;

    private float mDetaDegree;

    // 默认起始角度
    private float mDefaultDegree = 225f;

    /**
     * 最大角度，与最小角度,以下两个数值是在正常坐标系的角度
     * 需要考虑这是倒立的平面直角坐标系，这样就可以控制旋转的范围在正常的坐标系的[225°,-45°]之间
     */
    private float mMaxDegree = 45f;

    private float mMinDegree = -225f;

    private RectF mOvalRectF = new RectF();

    private Paint mMainPaint;

    private Paint mFirstPaint;

    private int mMaxProgress;

    private int mCurrentProgress;

    public RevolveGestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable bgDrawable = getBackground();
        if (bgDrawable != null && bgDrawable instanceof BitmapDrawable) {
            mBitmap = ((BitmapDrawable) bgDrawable).getBitmap();
        }
        initSize();
        mDetaDegree = -mDefaultDegree;
        mMainPaint = new Paint();
        mMainPaint.setAntiAlias(true);
        mMainPaint.setStyle(Paint.Style.STROKE);
        mMainPaint.setColor(0xFFFF0000);
        mMainPaint.setStrokeWidth(8);

        mFirstPaint = new Paint();
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setStyle(Paint.Style.STROKE);
        mFirstPaint.setColor(0xFF00FF00);
        mFirstPaint.setStrokeWidth(5);

        mMaxProgress = (int) (mMaxDegree - mMinDegree);
        mCurrentProgress = (int) Math.abs(mMinDegree - mDetaDegree);
    }

    public void setRevolveDrawableResource(int id) {
        BitmapDrawable drawable = (BitmapDrawable) getContext().getResources().getDrawable(id);
        mBitmap = drawable.getBitmap();
        initSize();
        postInvalidate();
    }

    private void initSize() {
        if (mBitmap == null) {
            return;
        }
        width = mBitmap.getWidth();
        height = mBitmap.getHeight();
        mMaxWidth = (int) Math.sqrt(width * width + height * height);
        mCenterX = mCenterY = mMaxWidth * 0.5f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mMaxWidth, mMaxWidth);

        float r = (width * 0.5f);

        mOvalRectF.top = mCenterY - r;
        mOvalRectF.left = mCenterX - r;
        mOvalRectF.right = mCenterX + r;
        mOvalRectF.bottom = mCenterY + r;
        Log.i(LOG_TAG, "Oval RectF: " + mOvalRectF.toString());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(LOG_TAG, "当前角度: " + mDetaDegree);
        Matrix matrix = new Matrix();
        // 设置转轴位置
        matrix.setTranslate((float) width / 2, (float) height / 2);
        // 开始转,正--逆时针；负--顺时针
        matrix.preRotate(mDetaDegree);
        // 转轴还原
        matrix.preTranslate(-(float) width / 2, -(float) height / 2);
        // 将位置送到view的中心
        matrix.postTranslate((float) (mMaxWidth - width) / 2, (float) (mMaxWidth - height) / 2);
        canvas.drawBitmap(mBitmap, matrix, null);
        canvas.drawArc(mOvalRectF, mMinDegree, mMaxProgress, false, mMainPaint);
        canvas.drawArc(mOvalRectF, mMinDegree, mCurrentProgress, false, mFirstPaint);
        super.onDraw(canvas);
    }

    private float mCurrentDegree = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBitmap == null) {

        }
        float lastX;
        float lastY;
        float currentX;
        float currentY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = event.getX();
                lastY = event.getY();
                mCurrentDegree = detaDegree(mCenterX, mCenterY, lastX, lastY);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                lastX = currentX = event.getX();
                lastY = currentY = event.getY();
                float degree = detaDegree(mCenterX, mCenterY, currentX, currentY);
                // 滑过的弧度增量
                float dete = degree - mCurrentDegree;
                // 如果小于-90度说明 它跨周了，需要特殊处理350->17,
                if (dete < -270) {
                    dete = dete + 360;
                    // 如果大于90度说明 它跨周了，需要特殊处理-350->-17,
                } else if (dete > 270) {
                    dete = dete - 360;
                }
                addDegree(dete);
                mCurrentDegree = degree;
                mCurrentProgress = (int) Math.abs(mMinDegree - mDetaDegree);
                invalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 计算以(originalX,originalY)为坐标圆点，建立直角体系，求出(targetX,targetY)坐标与x轴的夹角
     * 主要是利用反正切函数的知识求出夹角
     * <p>
     * 第一象限：θ=arctan|b|/|a|， <br>
     * 第二象限：θ=180°-arctan|b|/|a|，<br>
     * 第三象限：θ=180°+arctan|b|/|a|，<br>
     * 第四象限：θ=360°-arctan|b|/|a|，
     * </p>
     * <p>
     * 角度弧度转换:
     * </p>
     * <p>
     * 弧度 角度 <br>
     * ---- = ---- <br>
     * π 180°
     * </p>
     * 
     * @param originalX
     * @param originalY
     * @param targetX
     * @param targetY
     * @return
     */
    private float detaDegree(float originalX, float originalY, float targetX, float targetY) {
        float detaX = targetX - originalX;
        float detaY = targetY - originalY;
        // 弧度
        double d;
        // 坐标在四个象限里
        if (detaX != 0) {
            float tan = Math.abs(detaY / detaX);
            if (detaX > 0) {
                // 第一象限
                if (detaY >= 0) {
                    d = Math.atan(tan);
                } else {
                    // 第四象限
                    d = 2 * Math.PI - Math.atan(tan);
                }
            } else {
                if (detaY >= 0) {
                    // 第二象限
                    d = Math.PI - Math.atan(tan);
                } else {
                    // 第三象限
                    d = Math.PI + Math.atan(tan);
                }
            }
        } else {
            // 坐标在y轴上
            if (detaY > 0) {
                // 坐标在y>0上
                d = Math.PI / 2;
            } else {
                // 坐标在y<0上
                d = -Math.PI / 2;
            }
        }
        return (float) ((d * 180) / Math.PI);
    }

    private void addDegree(float degree) {
        mDetaDegree += degree;
        if (mDetaDegree > 360 || mDetaDegree < -360) {
            mDetaDegree = mDetaDegree % 360;
        }
        if (mDetaDegree > mMaxDegree) {
            mDetaDegree = mMaxDegree;
        } else if (mDetaDegree < mMinDegree) {
            mDetaDegree = mMinDegree;
        }
    }
    
    static class SavedState extends BaseSavedState {
        
        float detaDegree;
        int currentProgress;
        
        /**
         * Constructor called from {@link RevolveGestureView#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }
        
        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            detaDegree = in.readFloat();
            currentProgress = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(detaDegree);
            out.writeInt(currentProgress);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    
    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        
        ss.detaDegree = mDetaDegree;
        ss.currentProgress = mCurrentProgress;
        
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mCurrentProgress = ss.currentProgress;
        setDetaDegree(ss.detaDegree);
    }
    
    private void setDetaDegree(float degree){
        mDetaDegree = degree;
        postInvalidate();
    }
}
