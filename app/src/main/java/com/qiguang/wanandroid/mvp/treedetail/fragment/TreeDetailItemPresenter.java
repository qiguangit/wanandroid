package com.qiguang.wanandroid.mvp.treedetail.fragment;

import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午7:56
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeDetailItemPresenter extends BasePresenter<TreeDetailItemContract.View> implements TreeDetailItemContract.Presenter{

    public TreeDetailItemPresenter(TreeDetailItemContract.View view) {
        super(view);
    }

    @Override
    public void requestArticle(int page,int id) {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .articleById(page, id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ArticleBean>(view, true, null) {
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        view.setupArticle(articleBean);
                    }
                }));
    }

    @Override
    public void refresh(int page, int id) {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .articleById(page, id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ArticleBean>(view){
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        view.refresh(articleBean);
                    }
                }));
    }

    @Override
    public void loadMore(int page, int id) {
        view.loadMore(null);
    }
}
