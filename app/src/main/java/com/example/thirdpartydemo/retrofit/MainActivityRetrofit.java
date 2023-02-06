package com.example.thirdpartydemo.retrofit;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdpartydemo.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivityRetrofit extends AppCompatActivity {

    private static final String TAG = "MainActivityRetrofit";

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrofit);

        textView = findViewById(R.id.textView);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                textView.setText("run in  child thread");
//                Looper.prepare();
//                Toast.makeText(MainActivityRetrofit.this, "222", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//        }).start();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

            Request request = new Request.Builder()
                    .url("https://www.wanandroid.com//hotkey/json")
                    .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    Log.e(TAG,"response :"+response.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        Call<UserLoginResult> call = RetrofitClient.getServiceApi().userLogin("coder", "123");
//        call.enqueue(new Callback<UserLoginResult>() {
//            @Override
//            public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
//                UserLoginResult loginResult = response.body();
//                Log.e(TAG, loginResult.data.toString());
//            }
//
//            @Override
//            public void onFailure(Call<UserLoginResult> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }
}
