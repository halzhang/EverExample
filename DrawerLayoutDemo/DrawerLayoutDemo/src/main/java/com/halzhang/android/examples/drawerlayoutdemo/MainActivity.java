package com.halzhang.android.examples.drawerlayoutdemo;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A {@link DrawerLayout} usage Demo
 * @author zhanghanguo
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView mDrawerListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        String[] array = getResources().getStringArray(R.array.left_drawer_array);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, android.R.id.text1, array));
        mDrawerListView.setOnItemClickListener(this);
        mDrawerListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpened = mDrawerLayout.isDrawerOpen(mDrawerListView);
        menu.findItem(R.id.action_back).setVisible(!isDrawerOpened);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectDrawerListItem(i);
    }

    private void selectDrawerListItem(int position){
        Bundle bundle = new Bundle();
        bundle.putInt(MyFragment.ARG_INT,position);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        mDrawerLayout.closeDrawer(mDrawerListView);
        mDrawerListView.setItemChecked(position,true);
    }

}
