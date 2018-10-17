package com.qiguang.wanandroid.mvp.register;

import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:55
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface RegisterContract {
    interface Presenter extends IPresenter{
        /**
         * 检查输入的数据是否合法
         * @param username 用户名
         * @param password 密码
         * @param repassword 重复密码
         * @return
         */
        boolean checkInputData(String username,String password,String repassword);

        /**
         * 注册
         * @param username
         * @param password
         * @param repassword
         */
        void register(String username,String password,String repassword);
    }

    interface View extends IView{
        /**
         * 注册成功
         */
        void registerSuccess();

        /**
         * 注册失败
         * @param error 错误信息
         */
        void registerError(String error);
    }
}
