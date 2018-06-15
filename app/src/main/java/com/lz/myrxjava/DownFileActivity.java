package com.lz.myrxjava;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lz.myrxjava.retrofit.GetRequest_Interface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownFileActivity extends AppCompatActivity implements View.OnClickListener{

    private Retrofit retrofit;
    private GetRequest_Interface service;
    private Observable<ResponseBody> downFile;
    private TextView tv;
    private Button start, stop, restart;
    private long sumLength, curLength = 0;
    private boolean isStop = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataProgress (EventProgress eventProgress) {
        tv.setText("length: " + eventProgress.percent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_file);

        EventBus.getDefault().register(this);

        tv = findViewById(R.id.tv);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        restart = findViewById(R.id.restart);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        restart.setOnClickListener(this);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/")
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetRequest_Interface.class);
//        downFile = service.downFile("bytes=0-2172142");

    }

    private void down(String header, final boolean isReStart) {
        downFile = service.downFile(header);
        downFile.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        sumLength = responseBody.contentLength();
                        isStop = false;
                        writeFile(responseBody.byteStream(), isReStart);
                        System.out.println("content length ------ " + responseBody.contentLength());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void stop() {
        isStop = true;
    }


    private void writeFile(InputStream byteStream, boolean isReStart) {
        String path = this.getFilesDir() + "otherApp.apk";
        curLength = 0;
        File file = new File(path);
        if (file.exists() && ! isReStart) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            byte[] b = new byte[1024];

            int lenght = 0;

            while ((lenght = byteStream.read(b)) != -1) {
                if (isStop) {
                    break;
                }
                fos.write(b, 0, lenght);
                curLength += lenght;
                EventBus.getDefault().post(new EventProgress(curLength * 100 / sumLength));
            }
            System.out.println("file  size   " + file.length());
            byteStream.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                down("", false);
                break;

            case R.id.stop:
                stop();
                break;

            case R.id.restart:
                System.out.println("cur Lenght   " + curLength);
                down( "bytes=" + (curLength + 1) + "-2172142", true);
                break;
        }
    }


    class EventProgress {
        long percent = 0;
        public EventProgress(long i) {
            percent = i;
        }

    }

}
