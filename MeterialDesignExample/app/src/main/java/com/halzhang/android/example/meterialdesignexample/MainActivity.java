package com.halzhang.android.example.meterialdesignexample;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                View myView = findViewById(R.id.image);

                revealView(myView);

                revealView(findViewById(R.id.reveal));

//                startTransitionActivity();

            }
        });
    }

    /**
     * visible a view with reveal animation
     *
     * @param view previously invisible view
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealView(View view) {
        // previously invisible view

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = view.getBottom();// (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startTransitionActivity() {
        ImageView androidRobotView = (ImageView) findViewById(R.id.image);
        startActivity(new Intent(this, TransitionActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, androidRobotView, "robot").toBundle());
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
}
