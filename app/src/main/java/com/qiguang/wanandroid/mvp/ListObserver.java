package com.qiguang.wanandroid.mvp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-22 下午2:25
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class ListObserver<T> extends DisposableObserver<List<T>> {
    private IView view;
    private boolean isShowError = true;
    private String errorMsg = "发生了未知错误";

    public ListObserver(IView view, boolean isShowError, String errorMsg) {
        this.view = view;
        this.isShowError = isShowError;
        this.errorMsg = errorMsg;
    }

    public ListObserver(IView view) {
        this.view = view;
    }

    public ListObserver(IView view, String errorMsg) {
        this.view = view;
        this.errorMsg = errorMsg;
    }

    @Override
    public void onNext(List<T> list) {
        if (list != null && list.size() > 0) {
            onSuccess(list);
        }else if(view!=null&&isShowError){
            view.showError();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 成功时回调
     * @param list
     */
    public abstract void onSuccess(List<T> list);
}
