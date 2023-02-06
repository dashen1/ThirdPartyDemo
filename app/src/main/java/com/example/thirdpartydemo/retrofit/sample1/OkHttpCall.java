package com.example.thirdpartydemo.retrofit.sample1;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Response;

public class OkHttpCall<T> implements Call<T> {

    final ServiceMethod serviceMethod;
    final Object[] args;

    public OkHttpCall(ServiceMethod serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public void enqueue(Callback callback) {
        Log.e("TAG","发起请求");
        okhttp3.Call call = serviceMethod.createNewCall(args);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                if (callback!=null){
                    callback.onFailure(OkHttpCall.this,e);
                }
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                Log.e("TAG","onResponse success");
                Log.e("TAG",response.body().string());
                ResponseSample1 rResponse = new ResponseSample1();
                rResponse.body = serviceMethod.parseBody(response.body());

                if (callback !=null){
                    callback.onResponse(OkHttpCall.this, rResponse);
                }
            }
        });
    }
}
