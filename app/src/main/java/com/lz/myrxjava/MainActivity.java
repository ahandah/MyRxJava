package com.lz.myrxjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lz.myrxjava.MyRxJava.Subscribe;
import com.lz.myrxjava.retrofit.GetRequest_Interface;
import com.lz.myrxjava.retrofit.bean.Translation;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private GetRequest_Interface request_interface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Subscription mSubscription;
    private String TAG = "TAG";

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
//        创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribeOn(Schedulers.io()).subscribe(observer);


//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/")
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        request_interface = retrofit.create(GetRequest_Interface.class);
//
//        Observable.interval(2, 1, TimeUnit.SECONDS)
//                .doOnNext(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Observable<Translation> observable = request_interface.getCall();
//                        observable.subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Observer<Translation>() {
//                                    @Override
//                                    public void onSubscribe(Disposable d) {
//                                        compositeDisposable.add(d);
//                                    }
//
//                                    @Override
//                                    public void onNext(Translation translation) {
//                                        translation.show();
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        System.out.println("请求失败");
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//
//                                    }
//                                });
//                    }
//                })
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        System.out.println("on  Subscribe   ------  ");
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        System.out.println("on  Next   ------  " + aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("on  Error   ------  ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println("on  Complete   ------  ");
//                    }
//                });

//        demo4();

    }

//    public void request(View view) {
//        mSubscription.request(95); //请求96个事件
//    }
//
//    public void demo4() {
//        Flowable
//                .create(new FlowableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                        Log.d(TAG, "First requested = " + emitter.requested());
//                        boolean flag;
//                        for (int i = 0; ; i++) {
//                            flag = false;
//                            while (emitter.requested() == 0) {
//                                if (!flag) {
//                                    Log.d(TAG, "Oh no! I can't emit value!");
//                                    flag = true;
//                                }
//                            }
//                            emitter.onNext(i);
//                            Log.d(TAG, "emit " + i + " , requested = " + emitter.requested());
//                        }
//                    }
//                }, BackpressureStrategy.ERROR)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Integer>() {
//
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        Log.d(TAG, "onSubscribe");
//                        mSubscription = s;
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.d(TAG, "onNext: " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.w(TAG, "onError: ", t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete");
//                    }
//                });
//    }


    @Override
    protected void onStop() {
        super.onStop();
    }

}
