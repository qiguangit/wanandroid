package com.qiguang.wanandroid.mvp.main;

import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-16 上午10:08
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface MainContract {
    interface Presenter extends IPresenter{
        /**
         * 检查更新
         */
        void checkUpdate();

        /**
         * 检查通知权限
         */
        void checkNotificationPermission();
    }

    interface View extends IView{

    }
}
