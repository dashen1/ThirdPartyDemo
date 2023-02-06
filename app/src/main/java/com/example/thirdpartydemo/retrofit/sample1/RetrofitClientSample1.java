package com.example.thirdpartydemo.retrofit.sample1;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClientSample1  {

    private static final ServiceApiSample1 mServiceApi;

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/user/")
                .client((okhttp3.Call.Factory) okHttpClient)
                .build();
        mServiceApi = retrofit.create(ServiceApiSample1.class);
    }

    public static ServiceApiSample1 getServiceApi(){
        return mServiceApi;
    }
}
