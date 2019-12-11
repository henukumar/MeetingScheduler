package com.kalher.meetingscheduler.base;

import android.app.Application;
import android.content.Context;

public class MeetingScheduler extends Application {

    // Avoid accessing sCurrentBaseActivity from static context
    private static BaseActivity sCurrentBaseActivity;
    private static Context sApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
    }

    public static BaseActivity getsCurrentBaseActivity() {
        return sCurrentBaseActivity;
    }

    public static void setsCurrentBaseActivity(BaseActivity sCurrentBaseActivity) {
        MeetingScheduler.sCurrentBaseActivity = sCurrentBaseActivity;
    }

    public static Context getsApplicationContext() {
        return sApplicationContext;
    }

}
