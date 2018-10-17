package com.qiguang.wanandroid.mvp.detail;

import com.qiguang.wanandroid.bean.CollectBean;
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
 * @Date: 2018 下午2:42
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {
    public DetailPresenter(DetailContract.View view) {
        super(view);
    }

    @Override
    public void collect(int id) {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .collect(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<CollectBean>(view){
                    @Override
                    public void onSuccess(CollectBean collectBean) {
                        view.collect();
                    }
                }));
    }

    @Override
    public void uncollect(int id) {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .uncollect(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<CollectBean>(view){
                    @Override
                    public void onSuccess(CollectBean collectBean) {
                        view.uncollect();
                    }
                }));
    }
}
