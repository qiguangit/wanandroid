package com.qiguang.wanandroid.mvp.main.project.detail;

import com.qiguang.wanandroid.bean.ProjectDetailBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午9:48
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface ProjectDetailContract {
    interface Presenter extends IPresenter{
        /**
         * 请求项目详情页的数据
         */
        void requestProjectDetail();

        /**
         * 刷新
         */
        void refresh();

        /**
         * 加载更多
         */
        void loadMore();

        /**
         * 设置请求的参数
         * @param page
         * @param cid
         */
        void setParams(int page,int cid);
    }

    interface View extends IView{
        /**
         * 设置详情页数据
         * @param bean
         */
        void setupProjectDetail(ProjectDetailBean bean);

        /**
         * 刷新
         * @param bean
         */
        void refresh(ProjectDetailBean bean);

        /**
         * 加载更多
         * @param bean
         */
        void loadMore(ProjectDetailBean bean);
    }
}
