package com.lz.myrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lz.myrxjava.MyRxJava.Disposable;
import com.lz.myrxjava.MyRxJava.Observable;
import com.lz.myrxjava.MyRxJava.ObservableEmitter;
import com.lz.myrxjava.MyRxJava.ObservableOnSubscribe;
import com.lz.myrxjava.MyRxJava.Observer;
import com.lz.myrxjava.MyRxJava.Subscribe;

public class MyRxJavaActivity extends AppCompatActivity {

    private Observable observable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rx_java);

        System.out.println("My RxJava  Activity   ");

//        observable = Observable.getInstance(new Observer() {
//            @Override
//            public void subscribe() {
//                System.out.println("getInstance  -   subscribe   ");
//                observable.onNext("123");
//            }
//        });
//
//        observable.OnSubscribe(new Subscribe() {
//            @Override
//            public void onSubscribe(String s) {
//                System.out.println("onSubscribe   -    " + s);
//            }
//        });

        Observable.getInstance(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter observableEmitter) {
                observableEmitter.onNext("123");
                observableEmitter.onComplete();
                observableEmitter.onNext("123");
                observableEmitter.onNext("123");
                observableEmitter.onNext("123");
                observableEmitter.onNext("123");
                observableEmitter.onNext("123");
            }
        }).OnSubscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext   " + s);
            }
        });


    }
}
