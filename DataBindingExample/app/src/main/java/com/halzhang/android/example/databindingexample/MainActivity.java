package com.halzhang.android.example.databindingexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.halzhang.android.example.databindingexample.databinding.ActivityMainBinding;
import com.halzhang.android.example.databindingexample.databinding.ListItemBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<Course> mCourses = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User("username", "password");
        binding.setUser(user);

        listView = (ListView) findViewById(R.id.list);
        for (int i = 0; i < 100; i++) {
            Course course = new Course("name" + i, "teacher" + i, System.nanoTime());
            mCourses.add(course);
        }
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCourses.size();
        }

        @Override
        public Object getItem(int position) {
            return mCourses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                ListItemBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.list_item, parent, false);
                convertView = binding.getRoot();
                viewHolder = new ViewHolder();
                viewHolder.view = convertView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ListItemBinding binding = DataBindingUtil.bind(viewHolder.view);
            binding.setCourse(mCourses.get(position));
            return convertView;
        }

        class ViewHolder {
            View view;
        }
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
