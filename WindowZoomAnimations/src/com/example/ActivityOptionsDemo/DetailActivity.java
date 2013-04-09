package com.example.ActivityOptionsDemo;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

public class DetailActivity extends Activity {

    public static final String EXTRA_COLOUR = "EXTRA_COLOUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = new View(this);
        v.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        v.setBackgroundColor(getIntent().getIntExtra(EXTRA_COLOUR, 0));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //overridePendingTransition();

        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(v);
    }
}
