package com.halzhang.android.examples.calendareventexample;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Calendar;

/**
 * 日历功能
 * Created by Hal on 2016/11/18.
 */

public class CalenderUtils {

    public static void addEvent(Activity activity) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "100YY上课提醒")
                .putExtra(CalendarContract.Events.DESCRIPTION, "点击进入直播 http://www.edu24ol.com")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "上课地点广州校区")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        activity.startActivity(intent);

    }

}
