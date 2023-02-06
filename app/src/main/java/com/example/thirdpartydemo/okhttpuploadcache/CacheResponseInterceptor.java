package com.example.thirdpartydemo.okhttpuploadcache;


import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class CacheResponseInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(30, TimeUnit.SECONDS)
                .build();
        response = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //过期时间30秒
                .addHeader("Cache-Control", cacheControl.toString())
                .build();
        return response;
    }
}
