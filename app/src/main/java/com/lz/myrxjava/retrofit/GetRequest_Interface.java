package com.lz.myrxjava.retrofit;

import com.lz.myrxjava.retrofit.bean.Translation;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;

/**
 * Created by Administrator on 2018/5/23.
 */

public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

    @Streaming
    @GET("http://7xk9dj.com1.z0.glb.clouddn.com/BGAUpdateSample_v1.0.3_debug.apk")
    Observable<ResponseBody> downFile(@Header("Range") String range);
}
