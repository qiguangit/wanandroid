package com.qiguang.wanandroid.mvp.main.home;

import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.BannerBean;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;
import com.qiguang.wanandroid.view.LoadingPager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午7:08
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private int articleIndex = 0;
    private HomeFragment mFragment;


    public HomePresenter(HomeContract.View view) {
        super(view);
        mFragment = (HomeFragment) view;
    }


    @Override
    public void requestArticles() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getArticles(articleIndex)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ArticleBean>(view){
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        view.setupArticles(articleBean);
                        requestBanner();
                    }
                }));
    }

    @Override
    public void requestBanner() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getBanner()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<BannerBean>(view){

                    @Override
                    public void onSuccess(BannerBean bannerBean) {
                        view.setupBanner(bannerBean);
                    }
                }));
    }

    @Override
    public void refresh() {
        articleIndex = 0;
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getArticles(articleIndex)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ArticleBean>(view){

                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        view.setupArticles(articleBean);
                        requestBanner();
                    }
                }));
    }

    @Override
    public void loadMore() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getArticles(++articleIndex)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ArticleBean>(view){
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        view.loadMore(articleBean);
                    }
                }));
    }
}
