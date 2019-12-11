package com.kalher.meetingscheduler.datasource.network.interceptor;

import com.kalher.meetingscheduler.custom.exception.NoNetworkException;
import com.kalher.meetingscheduler.utils.NetworkUtility;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!NetworkUtility.isNetworkConnected()){
            throw new NoNetworkException();
        }
        return chain.proceed(chain.request());
    }

}
