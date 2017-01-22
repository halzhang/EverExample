package com.halzhang.android.example.jniexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private JNIEngine mJNIEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mJNIEngine = new JNIEngine();
        Log.i("MainActivity", String.valueOf(mJNIEngine.init(getApplicationContext())));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onTestLocalRefClick(View view) {
        boolean result = mJNIEngine.testLocalRef();
        Log.i(TAG, "onTestLocalRefClick: " + result);
    }

    public void onGetStringClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < Integer.MAX_VALUE) {
                    i++;
                    String string = mJNIEngine.createString();
                    Log.i(TAG, "onGetStringClick: " + string + " " + i);
                }
            }
        }).start();
    }

    public void onGetBundleClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 2000) {
                    i++;
                    Bundle data = mJNIEngine.getData();
                    if (data != null) {
                        int keyInt = data.getInt("key_int");
                        Log.i(TAG, "onGetBundleClick: " + keyInt + " call time: " + i);
                    }
                }
            }
        }).start();

    }
}
