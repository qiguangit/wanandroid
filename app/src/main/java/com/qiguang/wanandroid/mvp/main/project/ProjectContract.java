package com.qiguang.wanandroid.mvp.main.project;

import com.qiguang.wanandroid.bean.ProjectTitleBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:44
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface ProjectContract {
    interface Presenter extends IPresenter{
        void requestIndicator();
    }

    interface View extends IView{
        void setupIndicator(ProjectTitleBean bean);
    }
}
