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
        User user = new User("username", "password", "http://images.unsplash.com/photo-1454678904372-2ca94103eca4");
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
            ListItemBinding binding;
            if (convertView == null) {
                binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.list_item, parent, false);
                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ListItemBinding) convertView.getTag();
            }
            binding.setCourse(mCourses.get(position));
            return convertView;
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
