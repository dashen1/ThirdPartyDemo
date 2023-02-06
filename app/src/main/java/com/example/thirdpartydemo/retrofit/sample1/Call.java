package com.example.thirdpartydemo.retrofit.sample1;

public interface Call<T> {
    void enqueue(Callback<T> callback);
}
