package com.qiguang.wanandroid.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;

import com.qiguang.wanandroid.App;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午7:22
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class UIUtils {
    /**
     * 判断是否是主线程
     * @return
     */
    public static boolean isMainThread() {
        int myTid = Process.myTid();
        return App.mainThreadId == myTid;
    }

    /**
     * 将runnable 运行在主线程
     * @param runnable
     */
    public static void runOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            App.handler.post(runnable);
        }
    }

    public static int getColor(int id){
        return App.getContext().getResources().getColor(id);
    }

}
