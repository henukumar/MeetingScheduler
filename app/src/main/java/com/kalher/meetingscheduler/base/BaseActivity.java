package com.kalher.meetingscheduler.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    private String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.i(TAG + " onCreate");
        super.onCreate(savedInstanceState);
        MeetingScheduler.setsCurrentBaseActivity(this);
        setUI();
    }

    protected abstract void setUI();

    @Override
    protected void onStart() {
        super.onStart();
        Timber.i(TAG + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.i(TAG + " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.i(TAG + " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.i(TAG + " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.i(TAG + " onDestroy");
    }

}
