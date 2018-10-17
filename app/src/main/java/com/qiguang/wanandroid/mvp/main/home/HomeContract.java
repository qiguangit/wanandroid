package com.qiguang.wanandroid.mvp.main.home;

import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.BannerBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午12:42
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface HomeContract {

    interface Presenter extends IPresenter {
        /**
         * 请求文章列表
         */
        void requestArticles();

        /**
         * 请求banner
         */
        void requestBanner();

        /**
         * 刷新文章列表
         */
        void refresh();

        /**
         * 加载更多
         */
        void loadMore();
    }

    interface View extends IView {
        /**
         * 设置文章列表数据
         * @param articleBean 文章列表组成的bean
         */
        void setupArticles(ArticleBean articleBean);

        /**
         * 设置banner数据
         * @param bannerBean banner bean
         */
        void setupBanner(BannerBean bannerBean);

        /**
         * 刷新文章列表
         * @param articleBean 最新的数据
         */
        void refresh(ArticleBean articleBean);

        /**
         * 加载更多
         * @param articleBean 下一页的数据
         */
        void loadMore(ArticleBean articleBean);
    }

}
