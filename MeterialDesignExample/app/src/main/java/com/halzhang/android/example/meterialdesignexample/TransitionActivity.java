package com.halzhang.android.example.meterialdesignexample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class TransitionActivity extends AppCompatActivity {

    private static final String TAG = "TransitionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        setupWindowAnimation();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimation() {
        getWindow().getEnterTransition().setDuration(500);
        Transition transition = getWindow().getSharedElementEnterTransition();
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Log.i(TAG, "onTransitionEnd: " + transition.toString());
                transition.removeListener(this);
                showReveal();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            hideReveal();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showReveal() {
        View view = findViewById(R.id.content);
        View imageView = findViewById(R.id.robot);
        RevealUtils.revealShow(getApplicationContext(), imageView.getWidth() / 2, view, android.R.color.holo_blue_light, new RevealUtils.OnRevealAnimationListener() {
            @Override
            public void onAnimationEnd() {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.show();
                }
            }

            @Override
            public void onAnimationStart() {

            }
        });
    }

    private void hideReveal() {
        View view = findViewById(R.id.content);
        View imageView = findViewById(R.id.robot);
        RevealUtils.revealHide(getApplicationContext(), imageView.getWidth() / 2, view, android.R.color.holo_blue_light, new RevealUtils.OnRevealAnimationListener() {
            @Override
            public void onAnimationEnd() {
                onBackPressed();
            }

            @Override
            public void onAnimationStart() {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
