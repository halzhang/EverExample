
package com.halzhang.andnroid.examples.animationsexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private ImageView mImage;

    private ImageView mImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = (ImageView) findViewById(R.id.image);
        mImage1 = (ImageView) findViewById(R.id.image1);

        final AnimatorSet animatorSet = getAnimatorSet();
        final AnimatorSet animatorSet2 = createAnimatorSet1();

        findViewById(R.id.start_animation).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animatorSet2.cancel();
                animatorSet2.start();// 动画没有结束，不能重新开始，对于无限循环的动画如果要重新开始需要手动调用cancel
                animatorSet.cancel();
                animatorSet.start();
            }
        });
    }

    private AnimatorSet createAnimatorSet1() {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mImage1, View.ROTATION, 0, 360);
        rotationAnimator.setDuration(100);
        // rotationAnimator.setRepeatMode(11);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        PropertyValuesHolder pvhSx = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.5f);
        PropertyValuesHolder pvhSy = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.5f);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(mImage1, pvhSx, pvhSy);
        scaleAnimator.setDuration(1000);

        PropertyValuesHolder pvhTx = PropertyValuesHolder
                .ofFloat(View.TRANSLATION_X, 0, -150, -200);
        PropertyValuesHolder pvhTy = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0, -150, 500);
        ObjectAnimator translateAnimator = ObjectAnimator.ofPropertyValuesHolder(mImage1, pvhTx,
                pvhTy);
        translateAnimator.setDuration(1000);
        // translateAnimator.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimator).with(translateAnimator).with(rotationAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mImage1.setVisibility(View.VISIBLE);
            }
        });
        return animatorSet;
    }

    private AnimatorSet getAnimatorSet() {
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mImage, View.TRANSLATION_Y, 800);
        translateAnimator.setDuration(1000);
        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mImage.setVisibility(View.VISIBLE);
            }
        });
        translateAnimator.setInterpolator(new AccelerateInterpolator());

        PropertyValuesHolder pvhSx = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
        PropertyValuesHolder pvhSy = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(mImage, pvhSx, pvhSy);
        scaleAnimator.setRepeatCount(5);
        scaleAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        scaleAnimator.setInterpolator(new BounceInterpolator());
        scaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // mImage.setVisibility(View.INVISIBLE);
            }
        });

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImage, View.ALPHA, 0);
        alphaAnimator.setDuration(1000);
        alphaAnimator.setRepeatCount(1);
        alphaAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimator).after(translateAnimator).before(alphaAnimator);

        return animatorSet;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
