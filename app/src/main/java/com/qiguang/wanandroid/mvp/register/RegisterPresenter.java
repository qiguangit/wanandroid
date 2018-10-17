package com.qiguang.wanandroid.mvp.register;

import android.text.TextUtils;
import android.util.Log;

import com.qiguang.wanandroid.bean.LoginRegisterBean;
import com.qiguang.wanandroid.bean.TreeBean;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午11:03
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public boolean checkInputData(String username, String password, String repassword) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
            if (password.equals(repassword)) {
                return true;
            } else {
                view.registerError("两次密码不一致");
                return false;
            }
        } else {
            view.registerError("用户名或密码不能为空");
            return false;
        }
    }

    @Override
    public void register(String username, String password, String repassword) {
        if (!checkInputData(username, password, repassword)) {
            return;
        }
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .register(username, password, repassword)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<LoginRegisterBean>(view) {

                    @Override
                    public void onSuccess(LoginRegisterBean loginRegisterBean) {
                        view.registerSuccess();
                    }
                }));
    }
}
