package com.example.thirdpartydemo.rxjava.sample1;

import androidx.annotation.NonNull;

public class ObservableMap<T, R> extends Observable<R> {

    private final Observable<T> source;

    public ObservableMap(Observable<T> source, Function<T, R> function) {
        this.source = source;
        this.function = function;
    }

    private final Function<T,R> function;

    @Override
    protected void subscribeActual(Observer<R> observer) {
        source.subscribe(new MapObserver(observer,function));
    }

    private class MapObserver<T> implements Observer<T>{

        private final Observer<R> observer;
        private final Function<T, R> function;

        public MapObserver(Observer<R> observer, Function<T, R> function) {
            this.observer = observer;
            this.function = function;
        }


        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(@NonNull T item) {
            //String 转 Bitmap
            R apply = function.apply(item);
            //把Bitmap传出去
            observer.onNext(apply);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
