package com.example.thirdpartydemo.okhttpuploadcache;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivityUploadCache extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自定义缓存（要求：有望 30s内请求读缓存，无网直接都缓存）
        //okhttp自带的扩展有坑
        //利用okhttp拦截器
        String url = "https://www.baidu.com";
        final Request request = new Request.Builder()
                //加在最前
                .url(url)
                .build();
        // new RealCall发起请求
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        File cacheFile = new File("/sdcard/cache");
        Cache cache = new Cache(cacheFile,10*1024*1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .addInterceptor(new InternetCacheInterceptor(this))
                //加在最后 数据缓存 缓存时间 30秒
                .addNetworkInterceptor(new CacheResponseInterceptor())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG","onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.e("TAG","response.body : "+response.body().string());
                Log.e("TAG",response.cacheResponse()+" : "+response.networkResponse());
            }
        });

    }
}
