package com.qiguang.wanandroid.mvp.login;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.bean.LoginRegisterBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.event.UpdateLoginStateEvent;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {

    public LoginPresenter(LoginActivity view) {
        super(view);
    }


    @Override
    public boolean checkInputData(String userName, String password) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            return true;
        } else {
            view.error("用户名或密码不能为空");
            return false;
        }
    }

    @Override
    public void login(String userName, String password) {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .login(userName, password)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<LoginRegisterBean>(view){
                    @Override
                    public void onSuccess(LoginRegisterBean loginRegisterBean) {
                        //登陆成功，持久化用户名
                        SPUtils.getInstance().put(Constant.USERNAME,loginRegisterBean.getData().getUsername());
//                        SPUtils.getInstance().put(Constant.PASSWORD,loginRegisterBean.getData().getPassword());
                        SPUtils.getInstance().put(Constant.IS_LOGIN,true);
                        view.success();
                        EventBus.getDefault().post(new UpdateLoginStateEvent());
                    }
                }));
    }

}
