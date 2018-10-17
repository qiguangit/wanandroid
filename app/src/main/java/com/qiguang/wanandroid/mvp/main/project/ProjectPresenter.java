package com.qiguang.wanandroid.mvp.main.project;

import com.qiguang.wanandroid.bean.ProjectTitleBean;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:51
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter{
    public ProjectPresenter(ProjectContract.View view) {
        super(view);
    }

    @Override
    public void requestIndicator() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getProjectTitles()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ProjectTitleBean>(view){

                    @Override
                    public void onSuccess(ProjectTitleBean projectTitleBean) {
                        view.setupIndicator(projectTitleBean);
                    }
                }));
    }
}
