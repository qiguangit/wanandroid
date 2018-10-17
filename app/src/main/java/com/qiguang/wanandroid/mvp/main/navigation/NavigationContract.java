package com.qiguang.wanandroid.mvp.main.navigation;

import android.support.design.widget.NavigationView;

import com.qiguang.wanandroid.bean.NavigationBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:14
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface NavigationContract {

    interface Presenter extends IPresenter{
        /**
         * 请求导航数据
         */
        void requestNavigation();
    }

    interface View extends IView{
        /**
         * 设置导航栏的数据
         * @param bean
         */
        void setupNavigation(NavigationBean bean);
    }
}
