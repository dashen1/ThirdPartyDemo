package com.example.thirdpartydemo.rxjava.sample1;

public abstract class Observable<T> implements ObservableSource<T>{

    public static <T> Observable<T> just(T item){

        return onAssembly(new ObservableJust<T>(item));
    }

    private static <T> Observable<T> onAssembly(Observable<T> source) {

        return source;
    }

    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    public void subscribe(Consumer<T> onNext){
        subscribe(onNext, null, null);
    }

    public <R> Observable<R> map(Function<T,R> function){
        return onAssembly(new ObservableMap<>(this, function));
    }

    private void subscribe(Consumer<T> onNext,Consumer<T> error,Consumer<T> complete){
        subscribe(new LambdaObserver<>(onNext));
    }

    protected abstract void subscribeActual(Observer<T> observer);
}
