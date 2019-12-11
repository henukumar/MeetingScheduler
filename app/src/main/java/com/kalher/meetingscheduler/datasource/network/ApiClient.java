package com.kalher.meetingscheduler.datasource.network;

import com.kalher.meetingscheduler.datasource.network.interceptor.NetworkConnectionInterceptor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static WebService webService = null;

    public static WebService getMapClient(){
        if(webService == null){
            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(WebService.BASE_URL)
                    .client(getOkHttpClient())
                    .build();

            webService = retrofit.create(WebService.class);
        }
        return webService;
    }

    private static OkHttpClient getOkHttpClient(Interceptor... interceptors){
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        builder.addInterceptor(new NetworkConnectionInterceptor());
        for(Interceptor interceptor : interceptors){
            builder.addInterceptor(interceptor);
        }

        final OkHttpClient client = builder.build();
        return client;
    }

}
