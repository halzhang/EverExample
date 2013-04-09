package com.example.ActivityOptionsDemo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HomeActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        GridView grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(new HoloTilesAdapter());
    }


    public class HoloTilesAdapter extends BaseAdapter {

        private static final int TILES_COUNT = 60;

        private final int[] COLOURS = {getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.yellow)
        };

        private final int[] DRAWABLES = {R.drawable.blue_tile,
                R.drawable.green_tile,
                R.drawable.purple_tile,
                R.drawable.red_tile,
                R.drawable.yellow_tile
        };

        @Override
        public int getCount() {
            return TILES_COUNT;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView v;
            if (convertView == null) {
                v = (ImageView) getLayoutInflater().inflate(R.layout.holo_tile, parent, false);

            } else {
                v = (ImageView) convertView;
            }

            final int colour = COLOURS[position % 5];
            v.setImageDrawable(getResources().getDrawable(DRAWABLES[position % 5]));

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomeActivity.this, DetailActivity.class);
                    i.putExtra(DetailActivity.EXTRA_COLOUR, colour);
                    Bundle b = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        //b = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(),
                        //                                         view.getHeight()).toBundle();
                        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                        bitmap.eraseColor(colour);
                        b = ActivityOptions.makeThumbnailScaleUpAnimation(view, bitmap, 0, 0).toBundle();
                    }
                    startActivity(i, b);
                }
            }
            );
            return v;
        }
    }   // end HoloTilesAdapter
}
