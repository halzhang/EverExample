package com.halzhang.android.example.glideexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;

import java.util.ArrayList;
import java.util.List;

/**
 * How to use {@link com.bumptech.glide.ListPreloader}
 *
 * @author halzhang.github.io
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ListView listView;
    private ArrayList<MyModel> models = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO init views
        listView.setOnScrollListener(listPreloader);
    }

    private class MyModel {
        public String imgUrl;
    }

    private ListPreloader listPreloader = new ListPreloader<MyModel>(new ListPreloader.PreloadModelProvider<MyModel>() {
        @Override
        public List<MyModel> getPreloadItems(int position) {
            List<MyModel> preloads = new ArrayList<>(1);
            preloads.add(models.get(position));
            return preloads;
        }

        @Override
        public GenericRequestBuilder getPreloadRequestBuilder(MyModel item) {
            return Glide.with(MainActivity.this).load(item.imgUrl);
        }
    }, new ListPreloader.PreloadSizeProvider<MyModel>() {
        @Override
        public int[] getPreloadSize(MyModel item, int adapterPosition, int perItemPosition) {
            int[] size = {500, 500};
            return size;
        }
    }, 3);


}
