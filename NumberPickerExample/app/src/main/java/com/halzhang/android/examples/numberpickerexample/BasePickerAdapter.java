package com.halzhang.android.examples.numberpickerexample;

/**
 * Created by Hal on 16/3/26.
 */
public abstract class BasePickerAdapter {

    public abstract int getCount();

    public abstract int getItemId(int position);

    public abstract Object getItem(int position);

    public abstract String getText(int position);
}
