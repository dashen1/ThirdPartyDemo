package com.example.thirdpartydemo.okhttp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdpartydemo.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_okhttp);

        OkHttpClient client = new OkHttpClient();
        new Request.Builder().build();

        try {
            URL url = new URL("dd");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置缓存可用 读取本地缓存的数据，如果没有直接去服务器获取，
        // 如果有，首先判断缓存策略，然后判断有没有国企，没有过期直接拿缓存，
        // 如果过期了需要添加一些之前头部信息如下 If-Modified-Since，这个时候后台有可能会给你返回304，代表你还是可以拿本地缓存，每次读取到新的响应后做一次缓存

        /**
         * findHealthConnection()找一个链接，首先判断有没有健康的，没有就创建（创建Socket，握手连接），连接缓存
         */
        //Okhttp 是基于原生的Socket+Okio(原生的IO封装) 就可以操作Socket的输入输出，我们就可以向服务器读写数据
        //6、连接的三个核心类
        //1、RealConnection ConnectionPool
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}