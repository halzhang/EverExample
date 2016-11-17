package com.halzhang.android.examples.lockscreenexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenBroadcastReceiver";

    public ScreenBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.w(TAG, "onReceive: "+action);
        if (Intent.ACTION_SCREEN_OFF.equals(action) || Intent.ACTION_SCREEN_ON.equals(action)) {
            Intent lockscreen = new Intent(context, LockScreenActivity.class);
            lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lockscreen);
        }
    }
}
