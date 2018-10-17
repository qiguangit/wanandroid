package com.qiguang.wanandroid.mvp.collect;

import com.qiguang.wanandroid.bean.CollectListBean;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-23 下午6:23
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CollectListPresenter extends BasePresenter<CollectListContract.View> implements CollectListContract.Presenter {
    private int page = 0;

    public CollectListPresenter(CollectListContract.View view) {
        super(view);
    }

    @Override
    public void requestCollectList() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestCollectList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<CollectListBean>(view) {
                    @Override
                    public void onSuccess(CollectListBean bean) {
                        view.setupCollectList(bean);
                    }
                }));
    }

    @Override
    public void refresh() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestCollectList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<CollectListBean>(view) {
                    @Override
                    public void onSuccess(CollectListBean bean) {
                        view.refresh(bean);
                    }
                }));
    }

    @Override
    public void loadMore() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestCollectList(++page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<CollectListBean>(view) {
                    @Override
                    public void onSuccess(CollectListBean bean) {
                        view.loadMore(bean);
                    }
                }));
    }
}
