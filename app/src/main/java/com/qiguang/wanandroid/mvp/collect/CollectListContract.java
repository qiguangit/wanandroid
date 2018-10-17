package com.qiguang.wanandroid.mvp.collect;

import com.qiguang.wanandroid.bean.CollectListBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-23 下午6:19
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface CollectListContract {
    interface Presenter extends IPresenter{
        /**
         * 请求收藏列表
         */
        void requestCollectList();

        /**
         * 刷新数据
         */
        void refresh();

        /**
         * 加载更多
         */
        void loadMore();
    }

    interface View extends IView{
        /**
         * 设置收藏列表
         * @param bean
         */
        void setupCollectList(CollectListBean bean);

        /**
         * 刷新数据
         * @param bean
         */
        void refresh(CollectListBean bean);

        /**
         * 加载更多
         * @param bean
         */
        void loadMore(CollectListBean bean);
    }
}
