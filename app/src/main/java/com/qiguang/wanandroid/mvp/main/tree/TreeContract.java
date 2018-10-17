package com.qiguang.wanandroid.mvp.main.tree;

import com.qiguang.wanandroid.bean.TreeBean;
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
public interface TreeContract {

    interface Presenter extends IPresenter {
        /**
         * 请求文章列表
         */
        void requestTree();
        

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
         * 设置知识体系数据
         * @param treeBean 知识体系组成的bean
         */
        void setupTree(TreeBean treeBean);
        
        /**
         * 刷新知识体系列表
         * @param treeBean 最新的数据
         */
        void refresh(TreeBean treeBean);

        /**
         * 加载更多
         * @param treeBean 下一页的数据
         */
        void loadMore(TreeBean treeBean);
    }

}
