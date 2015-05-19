package com.halzhang.android.example.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;

/**
 * 瀑布流
 * Created by zhanghanguo@yy.com on 2015/4/23.
 */
public class WaterFallLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }
}
