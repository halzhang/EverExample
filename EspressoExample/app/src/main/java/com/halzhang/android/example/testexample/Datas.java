package com.halzhang.android.example.testexample;

import java.util.ArrayList;

/**
 * Data
 * Created by zhanghanguo@yy.com on 2015/2/27.
 */
public class Datas {

    public static final String DATA = "DATA-";

    public static ArrayList<String> getDatas(int size) {
        if (size < 1) {
            size = 50;
        }
        ArrayList<String> datas = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            datas.add(DATA + i);
        }
        return datas;
    }

}
