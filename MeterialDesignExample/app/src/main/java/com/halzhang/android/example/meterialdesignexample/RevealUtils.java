package com.halzhang.android.example.meterialdesignexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Hal on 2016/11/8.
 */
public class RevealUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void revealShow(final Context context, int startRadius, final View view, @ColorRes final int colorRes, @NonNull final OnRevealAnimationListener onRevealAnimationListener) {
        // previously invisible view

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                onRevealAnimationListener.onAnimationStart();
                view.setBackgroundColor(ContextCompat.getColor(context, colorRes));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // make the view visible and start the animation
                view.setVisibility(View.VISIBLE);
                onRevealAnimationListener.onAnimationEnd();
            }
        });
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void revealHide(final Context context, int finalRadius, final View view, @ColorRes final int colorRes, @NonNull final OnRevealAnimationListener onRevealAnimationListener) {
        // previously invisible view

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int startRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                onRevealAnimationListener.onAnimationStart();
                view.setBackgroundColor(ContextCompat.getColor(context, colorRes));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // make the view visible and start the animation
                view.setVisibility(View.INVISIBLE);
                onRevealAnimationListener.onAnimationEnd();
            }
        });
        anim.start();
    }

    public interface OnRevealAnimationListener {
        void onAnimationEnd();

        void onAnimationStart();
    }
}
