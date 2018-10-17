package com.qiguang.wanandroid.mvp.main.project.detail;

import com.qiguang.wanandroid.bean.ProjectDetailBean;
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
 * @Date: 2018 下午10:03
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ProjectDetailPresenter extends BasePresenter<ProjectDetailContract.View> implements ProjectDetailContract.Presenter{

    private int page;
    private int cid;

    public ProjectDetailPresenter(ProjectDetailContract.View view) {
        super(view);
    }


    @Override
    public void requestProjectDetail() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestProjectDetail(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ProjectDetailBean>(view){
                    @Override
                    public void onSuccess(ProjectDetailBean projectDetailBean) {
                        view.setupProjectDetail(projectDetailBean);
                    }
                }));
    }

    @Override
    public void refresh() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestProjectDetail(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ProjectDetailBean>(view){
                    @Override
                    public void onSuccess(ProjectDetailBean projectDetailBean) {
                        view.refresh(projectDetailBean);
                    }
                }));
    }

    @Override
    public void loadMore() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestProjectDetail(++page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ProjectDetailBean>(view){
                    @Override
                    public void onSuccess(ProjectDetailBean projectDetailBean) {
                        view.loadMore(projectDetailBean);
                    }
                }));
    }

    @Override
    public void setParams(int page, int cid) {
        this.page=page;
        this.cid=cid;
    }
}
