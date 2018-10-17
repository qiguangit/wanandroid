package com.qiguang.wanandroid.mvp.detail;

import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:35
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface DetailContract {
    interface Presenter extends IPresenter{
        /**
         * 收藏
         * @param id
         */
        void collect(int id);

        /**
         * 取消收藏
         * @param id
         */
        void uncollect(int id);
    }

    interface View extends IView{
        /**
         * 收藏后，更新状态
         */
        void collect();

        /**
         * 取消收藏后，更新状态
         */
        void uncollect();

        /**
         * 分享
         */
        void share();


        /**
         * 用起来浏览器打开
         */
        void otherOpen();
    }
}
