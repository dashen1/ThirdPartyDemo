package com.example.thirdpartydemo.rxjava.sample1;

public class ObservableJust<T> extends Observable<T> {

    private T item;

    public ObservableJust(T item) {
        this.item = item;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {

        ScalarDisposable sd = new ScalarDisposable(observer, item);
        observer.onSubscribe();
        sd.run();
    }


    private class ScalarDisposable {

        private final Observer<T> observer;
        private final T item;

        public ScalarDisposable(Observer<T> observer, T item) {
            this.observer = observer;
            this.item = item;
        }

        public void run() {
            try {
                observer.onNext(item);
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }
    }
}
