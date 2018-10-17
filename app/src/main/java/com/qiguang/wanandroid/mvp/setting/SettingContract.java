package com.qiguang.wanandroid.mvp.setting;

import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-25 下午3:39
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface SettingContract {
    interface Presenter extends IPresenter{
        /**
         * 控制是否自动缓存(json字符串)
         * @param isAutoCache
         */
        void switchAutoCache(boolean isAutoCache);

        /**
         * 控制是否显示夜间模式
         * @param isNightMode
         */
        void switchNightMode(boolean isNightMode);

        /**
         * 意见反馈
         */
        void feedback();

        /**
         * 清除缓存
         */
        void cleanCache();

        /**
         * 查询缓存大小
         *
         */
        void queryCacheSize();
    }

    interface View extends IView{
        /**
         * 切换缓存状态
         * @param isAutoCache
         */
        void switchAutoCache(Boolean isAutoCache);

        /**
         * 切换显示模式
         * @param isNightMode
         */
        void switchNightMode(boolean isNightMode);

        /**
         * 设置缓存大小
         * @param cache
         */
        void setupCacheSize(String cache);
    }
}
