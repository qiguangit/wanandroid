package com.qiguang.wanandroid.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.qiguang.wanandroid.bean.BaseBean;
import com.qiguang.wanandroid.exception.DataIsNullException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-20 下午10:10
 * @Description: 将observer显示错误信息部分抽取出来
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class CommonObserver<T extends BaseBean> extends DisposableObserver<T> {
    private IView view;
    private boolean isShowError=true;
    private String errorMsg="发生了未知错误";

    public CommonObserver(IView view, boolean isShowError, String errorMsg) {
        this.view = view;
        this.isShowError = isShowError;
        this.errorMsg = errorMsg;
    }

    public CommonObserver(IView view){
        this.view=view;
    }

    public CommonObserver(IView view, String errorMsg){
        this.view=view;
        this.errorMsg=errorMsg;
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof HttpException){
            view.showErrorMsg(errorMsg);
        }else if(e instanceof DataIsNullException){
            view.showErrorMsg(errorMsg);
        }
        if(isShowError){
            view.showError();
        }
        LogUtils.e(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            onError(new DataIsNullException());
            return;
        }
        if (t.getErrorCode() != 0 && view != null) {
            view.showErrorMsg(t.getErrorMsg());
            return;
        }
        onSuccess(t);
    }

    @Override
    public void onComplete() {
    }

    /**
     * 成功时，回调
     * @param t
     */
    public abstract void onSuccess(T t);
}
