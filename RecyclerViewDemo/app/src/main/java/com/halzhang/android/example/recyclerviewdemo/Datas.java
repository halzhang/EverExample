package com.halzhang.android.example.recyclerviewdemo;

import java.util.ArrayList;

/**
 * Created by zhanghanguo@yy.com on 2015/2/27.
 */
public class Datas {

    public static ArrayList<String> getDatas(int size) {
        if (size < 1) {
            size = 50;
        }
        ArrayList<String> datas = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            datas.add("data-" + i);
        }
        return datas;
    }

}
