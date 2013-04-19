/*
 * Copyright (C) 2013 HalZhang.
 *
 * http://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.halzhang.android.examples.dashclockextensionexample;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

/**
 * DashClockExtensionExample
 * <p>
 * </p>
 * 
 * @author <a href="http://weibo.com/halzhang">Hal</a>
 * @version Apr 17, 2013
 */
public class MyExtensionService extends DashClockExtension {
    private static final String TAG = MyExtensionService.class.getSimpleName();

    public static final String PREF_NAME = "pref_name";

    @Override
    protected void onInitialize(boolean isReconnect) {
        // TODO Auto-generated method stub
        super.onInitialize(isReconnect);
    }
    
    
    @Override
    protected void onUpdateData(int reason) {
        // Get preference value.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString(PREF_NAME, getString(R.string.pref_name_default));

        // Publish the extension data update.
        publishUpdate(new ExtensionData().visible(true).icon(R.drawable.ic_launcher)
                .status("Hello").expandedTitle("Hello, " + name + "!")
                .expandedBody("Thanks for checking out this example extension for DashClock.")
                .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))));
    }

}
