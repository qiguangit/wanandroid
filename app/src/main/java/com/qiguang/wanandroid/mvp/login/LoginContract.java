package com.qiguang.wanandroid.mvp.login;

import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:06
 * @Description: 登陆页面的契约
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface LoginContract {
    interface Presenter extends IPresenter {
        /**
         * 检查输入的数据
         *
         * @param userName 用户名
         * @param password 密码
         * @return
         */
        boolean checkInputData(String userName, String password);

        /**
         * 登陆
         * @param userName 用户名
         * @param password 密码
         */
        void login(String userName,String password);
    }

    interface View extends IView {
        /**
         * 登陆
         */
        void login();

        /**
         * 登陆成功
         */
        void success();

        /**
         * 跳转到注册界面
         */
        void gotoRegister();
    }
}
