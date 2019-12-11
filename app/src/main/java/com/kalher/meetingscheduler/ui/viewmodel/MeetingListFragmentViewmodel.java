package com.kalher.meetingscheduler.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kalher.meetingscheduler.custom.wrapper.DataWrapper;
import com.kalher.meetingscheduler.datasource.repository.MeetingRepository;
import com.kalher.meetingscheduler.model.Example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MeetingListFragmentViewmodel extends ViewModel {

    public Calendar currentSelectedDate = Calendar.getInstance();
    public List<Example> meetingScheduleList = new ArrayList<>();
    private HashMap<String, LiveData<DataWrapper>> meetingScheduleCachedLiveData = new HashMap<>();

    public LiveData<DataWrapper> getMeetingSchedule(String date){
        if(!meetingScheduleCachedLiveData.containsKey(date)){
            LiveData<DataWrapper> meetingScheduleLiveData = new MutableLiveData<>();
            meetingScheduleCachedLiveData.put(date, meetingScheduleLiveData);
        }

        DataWrapper data;
        if((data = meetingScheduleCachedLiveData.get(date).getValue()) == null
                || data.getData() == null){
            MeetingRepository.getsInstance().getMeetingSchedule(meetingScheduleCachedLiveData.get(date), date);
        }
        return meetingScheduleCachedLiveData.get(date);
    }
}
