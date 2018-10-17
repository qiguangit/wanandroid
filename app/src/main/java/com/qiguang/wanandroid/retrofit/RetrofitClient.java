package com.qiguang.wanandroid.retrofit;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.qiguang.wanandroid.App;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午9:04
 * @Description: 静态内部类实现单例
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class RetrofitClient {
    private RetrofitClient(){

    }

    private static class RetrofitHolder{
        private static File sFile=new File(App.getContext().getCacheDir(),"responses");

        private static OkHttpClient client=new OkHttpClient.Builder()
//                .connectTimeout(30,TimeUnit.SECONDS)
//                .addNetworkInterceptor(new ReceiverCookieIntercept())
                .addInterceptor(new ReceiverCookieIntercept())
                .cookieJar(new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(App.getContext())))
                //10M
                .cache(new Cache(sFile,10*1024*1024))
                .build();
        private static Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        private static Retrofit retrofitFeedback=new Retrofit.Builder()
//                .baseUrl(API.FEEDBACK_BASE_URL)
//                .client(client)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
    }

    public static Retrofit getInstance(){
        return RetrofitHolder.retrofit;
    }

//    public static Retrofit getFeedbackInstance(){
//        return RetrofitHolder.retrofitFeedback;
//    }
}
