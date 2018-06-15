package com.lz.myrxjava.retrofit.bean;

import android.util.Log;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Translation {
    private int status;
    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public void show() {
        System.out.println("RxJava ----- " + content.out);
    }
}
