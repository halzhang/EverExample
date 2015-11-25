package com.halzhang.android.examples.chromecustomtabs;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CustomTabs";

    private String mUrl = "http://xw.qq.com";

    @Bind(R.id.open)
    Button mOpen;

    private CustomTabsActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        mHelper = new CustomTabsActivityHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper.bindService(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.unBindService(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.open)
    public void onOpenClicked(final View view) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(mHelper.getSession());
        //设置 toolbar 颜色
        builder.setToolbarColor(Color.RED);
        //设置启动动画
        builder.setStartAnimations(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out);
        //设置退出动画
        builder.setExitAnimations(getApplicationContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_input_get));
        builder.addMenuItem("发送给", createPendingShareIntent());
        CustomTabsIntent customTabsIntent = builder.build();
        mHelper.launchUrl(this, mUrl, customTabsIntent, new CustomTabsActivityHelper.OnCustomTabsInvalidListener() {
            @Override
            public void onInvalid(String url) {
                Snackbar.make(view, url, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private PendingIntent createPendingShareIntent() {
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_TEXT, "hal 正在使用 Chrome Custom Tabs");
        return PendingIntent.getActivity(getApplicationContext(), 0, actionIntent, 0);
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
