package com.qiguang.wanandroid.mvp.main.navigation;

import com.qiguang.wanandroid.bean.NavigationBean;
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
 * @Date: 2018 下午10:25
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter{


    public NavigationPresenter(NavigationContract.View view) {
        super(view);
    }

    @Override
    public void requestNavigation() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getNavigation()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<NavigationBean>(view){
                    @Override
                    public void onSuccess(NavigationBean navigationBean) {
                        view.setupNavigation(navigationBean);
                    }
                }));
    }
}
