package com.halzhang.android.examples.servicelib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    public static final String EXTRA_COMMAND = "extra_command";
    public static final String EXTRA_DATA = "extra_data";

    public static final String COMMAND_SAVE = "save";
    public static final String COMMAND_FETCH = "fetch";
    private static final String TAG = "MyService";

    public String mData;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getStringExtra(EXTRA_COMMAND);
        Log.i(TAG, "onStartCommand: " + command);
        if (COMMAND_SAVE.equals(command)) {
            mData = intent.getStringExtra(EXTRA_DATA);
            Log.i(TAG, "onStartCommand: Date: " + mData);
        } else if (COMMAND_FETCH.equals(command)) {
            Log.i(TAG, "onStartCommand: Date: " + mData);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
