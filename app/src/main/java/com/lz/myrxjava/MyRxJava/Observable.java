package com.lz.myrxjava.MyRxJava;

/**
 * Created by Administrator on 2018/5/28.
 */

public class Observable {

    private static Observable observable;
    private static ObservableOnSubscribe observableOnSubscribe;
    private static Observer observer;
    private static MyEmitter myEmitter;

    private Observable() {

    }


    public static Observable getInstance(ObservableOnSubscribe newObservableOnSubscribe) {
        observableOnSubscribe = newObservableOnSubscribe;
        return observable == null ? new Observable() : observable;
    }

    public void OnSubscribe(Observer observer) {
        myEmitter = new MyEmitter();
        this.observer = observer;
        myEmitter.onSubscribe(new Disposable());
        observableOnSubscribe.subscribe(myEmitter);
    }


    class MyEmitter implements ObservableEmitter {
        private Disposable d;
        @Override
        public void onSubscribe(Disposable d) {
            d.isDisposalbe = true;
            this.d = d;
            observer.onSubscribe(d);
        }

        @Override
        public void onNext(String s) {
            if (d.isDisposalbe) {
                observer.onNext(s);
            }
        }

        @Override
        public void onComplete() {
            d.isDisposalbe = false;
        }


    }

}
