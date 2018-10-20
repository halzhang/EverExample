package com.halzhang.android.examples.fixtransactiontoolargeexception;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.evernote.android.state.State;
import com.livefront.bridge.Bridge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int LEN = 10;

    @State
    public ArrayList<String> mArrayList = new ArrayList<>(LEN);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bridge.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < LEN; i++) {
            mArrayList.add("MainActivity-onCreate");
        }
        findViewById(R.id.btn_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondActivity.start(view.getContext());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bridge.saveInstanceState(this, outState);
    }
}
