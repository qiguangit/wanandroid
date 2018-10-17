package com.qiguang.wanandroid.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:50
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class BasePresenter<V extends IView> implements IPresenter {
    protected V view;
    protected CompositeDisposable mDisposable;

    public BasePresenter(V view){
        this.view =view;
        mDisposable=new CompositeDisposable();
    }
    @Override
    public void attach() {

    }

    public void addSubscribe(DisposableObserver observer){
        if(observer!=null){
            mDisposable.add(observer);
        }
    }

    @Override
    public void detach() {
        mDisposable.clear();
    }

}
