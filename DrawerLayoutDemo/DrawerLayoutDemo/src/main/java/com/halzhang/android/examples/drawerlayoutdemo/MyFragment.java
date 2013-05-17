package com.halzhang.android.examples.drawerlayoutdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Hal on 13-5-17.
 */
public class MyFragment extends Fragment {

    private int position;
    public static final String ARG_INT = "arg_int";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt(ARG_INT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout mLinearLayout = new LinearLayout(getActivity());
        mLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLinearLayout.setGravity(Gravity.CENTER);
        TextView mTextView = new TextView(getActivity());
        mLinearLayout.addView(mTextView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(72.0f);
        mTextView.setText(String.valueOf(position));
        return mLinearLayout;
    }
}
