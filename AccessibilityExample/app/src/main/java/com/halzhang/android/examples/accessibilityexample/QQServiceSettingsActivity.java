package com.halzhang.android.examples.accessibilityexample;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;

import com.halzhang.android.examples.accessibilityexample.databinding.ActivityServiceSettingsBinding;
import com.halzhang.android.examples.accessibilityexample.models.SettingModel;

public class QQServiceSettingsActivity extends AppCompatActivity {

    private SettingPresenter mPresenter;
    private SettingModel mModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SettingPresenter(getApplicationContext());
        mModel = mPresenter.getSetting();
        ActivityServiceSettingsBinding activityServiceSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_service_settings);
        activityServiceSettingsBinding.setSettingPresenter(mPresenter);
        activityServiceSettingsBinding.setSettingModel(mModel);
    }

    @BindingAdapter("android:text")
    public static void setText(EditText view, String text) {
        switch (view.getId()) {
            case R.id.et_group_name:
                break;
            case R.id.et_send_count:
                break;
        }
    }

}
