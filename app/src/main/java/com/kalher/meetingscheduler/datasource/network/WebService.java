package com.kalher.meetingscheduler.datasource.network;

import com.kalher.meetingscheduler.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    String BASE_URL = "http://fathomless-shelf-5846.herokuapp.com/api/";

    @GET("schedule")
    Call<List<Example>> getMeetingSchedule(@Query("date") String date);

}
