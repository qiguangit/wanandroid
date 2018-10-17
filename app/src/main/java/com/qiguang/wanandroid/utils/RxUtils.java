package com.qiguang.wanandroid.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-22 下午12:22
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class RxUtils {
    public static <T>ObservableTransformer<T,T> rxSchedulerHelper(){
        return observable->observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
