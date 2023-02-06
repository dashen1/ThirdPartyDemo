package com.example.thirdpartydemo.retrofit.sample1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdpartydemo.R;

public class MainActivityRetrofitSample1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        RetrofitClientSample1.getServiceApi().userLogin("bboy_coder","coder123").enqueue(new Callback<UserLoginResult>() {
            @Override
            public void onResponse(Call<UserLoginResult> call, ResponseSample1<UserLoginResult> responseSample1) {

            }

            @Override
            public void onFailure(Call<UserLoginResult> call, Throwable t) {

            }
        });
    }
}
