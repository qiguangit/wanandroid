package com.qiguang.wanandroid;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.common.Constant;
import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午9:37
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class App extends Application{
    private static Context mContext;
    public static Handler handler;
    public static Integer mainThreadId;
    public static Thread mainThread;

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        boolean isNight = SPUtils.getInstance().getBoolean(Constant.Setting.IS_NIGHT_MODE);
        AppCompatDelegate.setDefaultNightMode(isNight?AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_NO);
        mContext = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        mainThread = Thread.currentThread();
        //抓取内存泄露
        LeakCanary.install(this);
        //收集崩溃日志，并上传
        JPushInterface.initCrashHandler(this);
        //极光推送 当不接受推送为false时，初始化推送，解决Application比SplashActivity早执行的问题
        if(!SPUtils.getInstance().getBoolean(Constant.Setting.IS_NO_PUSH)){
            JPushInterface.init(this);
            JPushInterface.setDebugMode(true);
        }

        //极光统计
        JAnalyticsInterface.init(this);
        JAnalyticsInterface.setDebugMode(true);
    }

}
