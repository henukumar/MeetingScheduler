package com.kalher.meetingscheduler.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.kalher.meetingscheduler.R;
import com.kalher.meetingscheduler.base.BaseActivity;
import com.kalher.meetingscheduler.base.MeetingScheduler;
import com.kalher.meetingscheduler.databinding.ActivityMainBinding;
import com.kalher.meetingscheduler.ui.fragment.MeetingListFragment;
import com.kalher.meetingscheduler.ui.fragment.ScheduleMeetingFragment;

public class MainActivity extends BaseActivity implements MeetingListFragment.OnFragmentInteractionListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        // to avoid addition of fragment on rotation
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            setMeetingListFragment();
        }
    }

    private void openScheduleMeetingFragment(){
        ScheduleMeetingFragment meetingListFragment = ScheduleMeetingFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_fragment_container, meetingListFragment, "frag_schedule_meeting");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setMeetingListFragment(){
        MeetingListFragment meetingListFragment = MeetingListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_fragment_container, meetingListFragment, "frag_meeting_list");
        transaction.commitNow();
    }

    @Override
    public void onFragmentInteraction(int viewId) {
        switch (viewId){
            case R.id.btn_schedule_meeting:
                openScheduleMeetingFragment();
                break;
        }
    }

}
