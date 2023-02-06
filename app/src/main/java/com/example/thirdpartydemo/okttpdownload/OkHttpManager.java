package com.example.thirdpartydemo.okttpdownload;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpManager {

    private final static OkHttpManager sManager = new OkHttpManager();
    OkHttpClient okHttpClient;
    public OkHttpManager() {
        this.okHttpClient = new OkHttpClient();
    }

    public Call asyncCall(String url){

        Request request = new Request.Builder()
                .url(url)
                .build();
        return okHttpClient.newCall(request);
    }

    public static OkHttpManager getInstance(){
        return sManager;
    }
}
