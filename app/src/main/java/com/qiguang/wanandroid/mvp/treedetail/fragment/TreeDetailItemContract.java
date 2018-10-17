package com.qiguang.wanandroid.mvp.treedetail.fragment;

import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午5:50
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface TreeDetailItemContract {
    interface Presenter extends IPresenter{

        /**
         * 请求文章数据
         * @param page
         * @param id
         */
        void requestArticle(int page,int id);

        /**
         * 刷新数据
         * @param page
         * @param id
         */
        void refresh(int page,int id);

        /**
         * 加载更多
         * @param page
         * @param id
         */
        void loadMore(int page,int id);
    }

    interface View extends IView{
        /**
         * 设置文章数据
         * @param bean
         */
        void setupArticle(ArticleBean bean);

        /**
         * 刷新界面
         * @param bean
         */
        void refresh(ArticleBean bean);

        /**
         * 加载更多
         * @param bean
         */
        void loadMore(ArticleBean bean);
    }
}
