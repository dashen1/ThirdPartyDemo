package com.example.thirdpartydemo.retrofit.sample1;

public interface Callback<T> {

    void onResponse(Call<T> call, ResponseSample1<T> responseSample1);

    void onFailure(Call<T> call,Throwable t);
}
