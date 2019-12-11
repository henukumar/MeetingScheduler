package com.kalher.meetingscheduler.datasource.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kalher.meetingscheduler.custom.exception.NoNetworkException;
import com.kalher.meetingscheduler.custom.wrapper.DataWrapper;
import com.kalher.meetingscheduler.custom.Status;
import com.kalher.meetingscheduler.datasource.network.ApiClient;
import com.kalher.meetingscheduler.helper.MeetingScheduleComparator;
import com.kalher.meetingscheduler.model.Example;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingRepository {

    private static MeetingRepository sInstance;

    public static MeetingRepository getsInstance(){
        if(sInstance == null){
            sInstance = new MeetingRepository();
        }
        return sInstance;
    }

    public void getMeetingSchedule(final LiveData<DataWrapper> meetingScheduleLiveData, String date){
        Call<List<Example>> call = ApiClient.getMapClient().getMeetingSchedule(date);
        call.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                DataWrapper dataWrapper;
                if(response != null && response.body() != null){
                    Collections.sort(response.body(), new MeetingScheduleComparator());
                    dataWrapper = new DataWrapper(Status.SUCCESS, response.body());
                }else{
                    dataWrapper = new DataWrapper(Status.FAILED, null);
                }
                ((MutableLiveData) meetingScheduleLiveData).setValue(dataWrapper);
            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                DataWrapper dataWrapper;
                if(t instanceof NoNetworkException){
                    dataWrapper = new DataWrapper(Status.NO_NETWORK_CONNECTION, null);
                }else {
                    dataWrapper = new DataWrapper(Status.FAILED, null);
                }
                ((MutableLiveData) meetingScheduleLiveData).setValue(dataWrapper);
            }
        });
    }

}
