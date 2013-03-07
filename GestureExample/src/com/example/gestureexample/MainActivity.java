
package com.example.gestureexample;

import com.example.gestureexample.DefaultOnGestureListener.OnGestureFlingListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity implements OnGestureFlingListener {
    
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DefaultOnGestureListener listener = new DefaultOnGestureListener(getApplicationContext());
        listener.setOnGestureFlingListener(this);
        mGestureDetector = new GestureDetector(this, listener);
        setContentView(R.layout.activity_main);
        findViewById(R.id.root).setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onSingleFlingLeft(View view) {
        Log.i(LOG_TAG, "onSingleFlingLeft");
        return false;
    }

    @Override
    public boolean onSingleFlingRight(View view) {
        Log.i(LOG_TAG, "onSingleFlingRight");
        return false;
    }

    @Override
    public boolean onSingleFlingUp(View view) {
        Log.i(LOG_TAG, "onSingleFlingUp");
        return false;
    }

    @Override
    public boolean onSingleFlingDown(View view) {
        Log.i(LOG_TAG, "onSingleFlingDown");
        return false;
    }

}
