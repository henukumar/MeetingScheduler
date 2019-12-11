package com.kalher.meetingscheduler.utils;

import android.net.ConnectivityManager;

import com.kalher.meetingscheduler.base.MeetingScheduler;

public class NetworkUtility {

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) MeetingScheduler.getsApplicationContext()
                .getSystemService(MeetingScheduler.getsApplicationContext().CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
