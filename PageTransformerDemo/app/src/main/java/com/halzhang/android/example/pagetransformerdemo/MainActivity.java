package com.halzhang.android.example.pagetransformerdemo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "PageTransformer";

    private static final int SIZE = 3;

    private ViewPager mViewPager;

    private View[] mViews = new View[SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setPageTransformer(false, pageTransformer);
        for (int i = 0; i < mViews.length; i++) {
            mViews[i] = getLayoutInflater().inflate(R.layout.item, null);
            TextView view = (TextView) mViews[i].findViewById(R.id.text);
            view.setText(String.valueOf(i));
            mViews[i].setTag(i);
        }
        mViewPager.setAdapter(new MyViewPagerAdapter());
    }

    private ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {

        private static final float MIN_SCALE = 0.75f;

        /**
         * Apply a property transformation to the given page.
         *
         * @param page Apply the transformation to this page
         * @param position Position of page relative to the current front-and-center
         *                 position of the pager. 0 is front and center. 1 is one full
         *                 page position to the right, and -1 is one page position to the left.
         */
        @Override
        public void transformPage(View page, float position) {
            //分析
            int viewIndex = (int) page.getTag();
            Log.d(LOG_TAG, "ViewIndex: " + viewIndex + " position: " + position);

            // The >= 1 is needed so that the page
            // (page A) that transitions behind the newly visible
            // page (page B) that comes in from the left does not
            // get the touch events because it is still on screen
            // (page A is still technically on screen despite being
            // invisible). This makes sure that when the transition
            // has completely finished, we revert it to its default
            // behavior and move it off of the screen.

//            if (position < 0 || position >= 1.f) {
//                /*
//                向左滚动，这是要出去的那个页面；
//                向右滚动，这是要进来的那个页面
//                 */
//                page.setTranslationX(0);
//                page.setAlpha(1.f);
//                page.setScaleX(1);
//                page.setScaleY(1);
//                page.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
//
//                int width = page.getWidth();
//
//                TextView view = (TextView) page.findViewById(R.id.text);
//                float x = (float) (view.getX() + width * 0.5 * position * 0.1);
//                Log.d(LOG_TAG, "ViewIndex: " + viewIndex + " TextView x: " + x);
//                view.setX(x);
//
//            } else {
//                /*
//                向左滚动，这是要进来的那个页面；
//                向右滚动，这是要出去的那个页面
//                 */
//                page.setTranslationX(-position * page.getWidth());
//                page.setAlpha(Math.max(0, 1.f - position));
//                final float scale = Math.max(0, 1.f - position * 0.3f);
//                page.setScaleX(scale);
//                page.setScaleY(scale);
//                page.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
//            }


            int pageWidth = page.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                //TODO 应该理解成：View的位置处于[-1,0] 这个区间时，做什么处理
                // Use the default slide transition when moving to the left page
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                //TODO 应该理解成：View 的位置处于（0，1] 这个区间时，做什么处理
                // Fade the page out.
                page.setAlpha(1 - position);

                // Counteract the default slide transition
                page.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);
            }
        }
    };

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews[position];
            container.addView(view);
            return mViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews[position]);
        }

        @Override
        public int getCount() {
            return mViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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
