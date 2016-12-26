package com.halzhang.android.examples.accessibilityexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.halzhang.android.examples.accessibilityexample.models.SettingModel;

/**
 * Created by Hal on 16/12/23.
 */

public class SettingPresenter {

    private static final String TAG = "SettingPresenter";
    private Context mContext;

    public SettingPresenter(Context context) {
        mContext = context;
    }

    public void onSave(SettingModel settingModel) {
        Log.i(TAG, "onSave: " + settingModel.toString());
        SharedPreferences preferences = mContext.getSharedPreferences("qas.xml", 0);
        preferences.edit().putString("key_qq_group_name", settingModel.getQGroupName())
                .putInt("key_send_count", settingModel.getSendCount()).apply();
    }

    public SettingModel getSetting() {
        SharedPreferences preferences = mContext.getSharedPreferences("qas.xml", 0);
        String groupName = preferences.getString("key_qq_group_name", "");
        int sendCount = preferences.getInt("key_send_count", 0);
        return new SettingModel(groupName, sendCount);
    }

}
