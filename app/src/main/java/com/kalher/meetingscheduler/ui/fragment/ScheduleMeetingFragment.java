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

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kalher.meetingscheduler.R;
import com.kalher.meetingscheduler.databinding.FragmentScheduleMeetingBinding;
import com.kalher.meetingscheduler.model.Example;
import com.kalher.meetingscheduler.ui.viewmodel.MeetingListFragmentViewmodel;
import com.kalher.meetingscheduler.utils.DateUtility;

import java.util.Calendar;
import java.util.List;


public class ScheduleMeetingFragment extends Fragment implements View.OnClickListener {

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
        mViewModel = ViewModelProviders.of(getActivity()).get(MeetingListFragmentViewmodel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_meeting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        if(mViewModel.meetingDate != null){
//            binding.tvMeetingDate.setText(DateUtility.getDateDDMMYYYY(mViewModel.meetingDate));
//        }
//        if(mViewModel.meetingStartTime != null){
//            binding.tvStartTime.setText(DateUtility.getTimeHHMM(mViewModel.meetingStartTime));
//        }
//        if(mViewModel.meetingEndTime != null){
//            binding.tvEndTime.setText(DateUtility.getTimeHHMM(mViewModel.meetingEndTime));
//        }
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
                        mViewModel.meetingDate = calendar;
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
                        if(viewId == R.id.tv_start_time){
                            mViewModel.meetingStartTime = calendar;
                        }
                        if(viewId == R.id.tv_end_time){
                            mViewModel.meetingEndTime = calendar;
                        }

                        ((TextView) getActivity().findViewById(viewId)).setText(DateUtility.getTimeHHMM(calendar));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void scheduleMeeting(){

    }

    private void checkForscheduling(List<Example> meetingScheduleList){

    }

}
