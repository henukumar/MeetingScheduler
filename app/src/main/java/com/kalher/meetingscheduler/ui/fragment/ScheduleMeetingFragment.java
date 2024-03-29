package com.kalher.meetingscheduler.ui.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kalher.meetingscheduler.R;
import com.kalher.meetingscheduler.custom.wrapper.DataWrapper;
import com.kalher.meetingscheduler.databinding.FragmentScheduleMeetingBinding;
import com.kalher.meetingscheduler.helper.MeetingScheduleComparator;
import com.kalher.meetingscheduler.model.Example;
import com.kalher.meetingscheduler.ui.viewmodel.MeetingListFragmentViewmodel;
import com.kalher.meetingscheduler.utils.DateUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ScheduleMeetingFragment extends Fragment implements View.OnClickListener {

    private final String KEY_MEETING_DATE = "meeting_date";
    private final String KEY_START_TIME = "start_time";
    private final String KEY_END_TIME = "end_time";

    private MeetingListFragmentViewmodel mViewModel;
    private FragmentScheduleMeetingBinding binding;

    public ScheduleMeetingFragment() {
    }

    public static ScheduleMeetingFragment newInstance() {
        ScheduleMeetingFragment fragment = new ScheduleMeetingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mViewModel = ViewModelProviders.of(getActivity()).get(MeetingListFragmentViewmodel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_meeting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_MEETING_DATE, binding.tvMeetingDate.getText().toString());
        outState.putString(KEY_START_TIME, binding.tvStartTime.getText().toString());
        outState.putString(KEY_END_TIME, binding.tvEndTime.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            if(savedInstanceState.getString(KEY_MEETING_DATE) != null){
                binding.tvMeetingDate.setText(savedInstanceState.getString(KEY_MEETING_DATE));
            }
            if(savedInstanceState.getString(KEY_START_TIME) != null){
                binding.tvStartTime.setText(savedInstanceState.getString(KEY_START_TIME));
            }
            if(savedInstanceState.getString(KEY_END_TIME) != null){
                binding.tvEndTime.setText(savedInstanceState.getString(KEY_END_TIME));
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setClickListener();
    }

    private void setClickListener(){
        binding.ivBack.setOnClickListener(this);
        binding.tvMsgBack.setOnClickListener(this);
        binding.tvMeetingDate.setOnClickListener(this);
        binding.tvStartTime.setOnClickListener(this);
        binding.tvEndTime.setOnClickListener(this);
        binding.etDescription.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.tv_msg_back:
                getActivity().onBackPressed();
                break;

            case R.id.tv_meeting_date:
                pickDate(v.getId());
                break;

            case R.id.tv_start_time:
            case R.id.tv_end_time:
                pickTime(v.getId());
                break;

            case R.id.btn_submit:
                scheduleMeeting();
                break;
        }
    }

    private void pickDate(final int viewId){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        ((TextView) getActivity().findViewById(viewId))
                                .setText(DateUtility.getDateDDMMYYYY(calendar));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void pickTime(final int viewId){
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        ((TextView) getActivity().findViewById(viewId)).setText(DateUtility.getTimeHHMM(calendar));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void scheduleMeeting(){
        final LiveData<DataWrapper> data = mViewModel
                .getMeetingSchedule(DateUtility.getDateDDMMYYYY(mViewModel.currentSelectedDate));

        if (data.getValue() != null) {
            checkForscheduling(data.getValue());
        }else{
            binding.llProgressbar.setVisibility(View.VISIBLE);
        }
        data.observe(getViewLifecycleOwner(), new Observer<DataWrapper>() {
            @Override
            public void onChanged(DataWrapper dataWrapper) {
                checkForscheduling(dataWrapper);
            }
        });
    }

    private boolean checkForscheduling(DataWrapper dataWrapper){
        boolean isSlotAvailable = true;

        if(dataWrapper != null && dataWrapper.getData() != null){
            List<Example> meetingScheduleList = new ArrayList<>((List<Example>) dataWrapper.getData());

            Example meetingSchedule = new Example();
            meetingSchedule.setStartTime(binding.tvStartTime.getText().toString());
            meetingSchedule.setEndTime(binding.tvEndTime.getText().toString());

            meetingScheduleList.add(meetingSchedule);

            Collections.sort(meetingScheduleList, new MeetingScheduleComparator());

            int index = 0;
            for(int i=0;i<meetingScheduleList.size();i++){
                if(meetingScheduleList.get(i) == meetingSchedule){
                    index = i;
                    break;
                }
            }

            if(index-1 >= 0 && index+1 < meetingScheduleList.size()){
                if(DateUtility.timeToCalendar(meetingScheduleList.get(index-1).getEndTime())
                        .after(DateUtility.timeToCalendar(meetingSchedule.getStartTime()))
                        || DateUtility.timeToCalendar(meetingScheduleList.get(index+1).getStartTime())
                        .before(DateUtility.timeToCalendar(meetingSchedule.getEndTime()))){
                    isSlotAvailable = false;
                }
            }else if(index-1 >= 0){
                if(DateUtility.timeToCalendar(meetingScheduleList.get(index-1).getEndTime())
                        .after(DateUtility.timeToCalendar(meetingSchedule.getStartTime()))){
                    isSlotAvailable = false;
                }
            }else if(index+1 < meetingScheduleList.size()){
                if(DateUtility.timeToCalendar(meetingScheduleList.get(index+1).getStartTime())
                        .before(DateUtility.timeToCalendar(meetingSchedule.getEndTime()))){
                    isSlotAvailable = false;
                }
            }

            binding.llProgressbar.setVisibility(View.GONE);

            String messageAvailable = getString(R.string.msg_slot_available);
            String messageNotAvailable = getString(R.string.msg_no_slot_available);

            Toast.makeText(getActivity(), isSlotAvailable ? messageAvailable : messageNotAvailable,
                    Toast.LENGTH_SHORT).show();

        }
        return isSlotAvailable;
    }

}
