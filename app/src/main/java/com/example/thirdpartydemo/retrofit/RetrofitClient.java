package com.example.thirdpartydemo.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final static ServiceApi mServiceApi;

    static {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.126.72.11:8088/RetrofitWebDemo/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mServiceApi = retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getServiceApi(){
        return mServiceApi;
    }
}
