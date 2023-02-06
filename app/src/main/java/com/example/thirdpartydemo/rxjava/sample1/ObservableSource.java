package com.example.thirdpartydemo.rxjava.sample1;

public interface ObservableSource<T> {
    void subscribe(Observer<T> observer);
}
