package com.lz.myrxjava.MyRxJava;

/**
 * Created by Administrator on 2018/5/28.
 */

public interface Observer {

//    void subscribe();
    void onSubscribe(Disposable d);
    void onNext(String s);
}
