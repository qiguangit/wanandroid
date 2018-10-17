package com.qiguang.wanandroid.common;

import android.app.Activity;

import java.util.Stack;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018-07-17 13:47
 * @Version: V1.0
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class AppManager {
    private static AppManager mManager = new AppManager();
    private Stack<Activity> mStack = new Stack<>();

    private AppManager() {
    }

    public static AppManager getInstance() {
        return mManager;
    }

    public void add(Activity activity) {
        mStack.push(activity);
    }

    public Activity get(int index) {
        return mStack.get(index);
    }

    public Activity getTop() {
        return mStack.peek();
    }

    public Activity removeTop() {
        return mStack.pop();
    }

    public void removeStack(Activity activity){
        mStack.remove(activity);
    }

    public void removeActivity(Activity activity) {
        mStack.remove(activity);
        activity.finish();
        if(mStack.isEmpty()){
            exitApp();
        }
    }

    public boolean isBottom(){
        return mStack.size()==1;
    }

    public void removeAll() {
        for (int i=mStack.size()-1;i>=0;i--){
            Activity remove = mStack.remove(i);
            remove.finish();
        }
        exitApp();
    }

    private void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
