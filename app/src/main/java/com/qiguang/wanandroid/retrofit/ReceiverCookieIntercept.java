package com.qiguang.wanandroid.retrofit;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:52
 * @Description: 拦截并持久化cookie
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ReceiverCookieIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        if (!NetworkUtils.isConnected()) {
            //当没有网络时，使用缓存
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        Response response = chain.proceed(request);

        if (NetworkUtils.isConnected()) {
            String cacheControl = request.cacheControl().toString();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            //无网络时 设置超时时间为1周
            int maxStale = 60 * 60 * 24 * 7;
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
