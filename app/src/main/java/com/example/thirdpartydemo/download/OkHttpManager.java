package com.example.thirdpartydemo.download;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpManager {

    private static final OkHttpManager sMANAGER = new OkHttpManager();
    private OkHttpClient okHttpClient;

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
        return sMANAGER;
    }

    public Response syncResponse(String mUrl, long start, long end, long progress) throws IOException {
        Log.e("TAG","syncResponse start = "+ (start+progress));
        Log.e("TAG","syncResponse end = "+ end);
        Request request = new Request.Builder().url(mUrl)
                .addHeader("Range","bytes="+(start+progress)+"-"+end)
                .build();
        return okHttpClient.newCall(request).execute();
    }
}
