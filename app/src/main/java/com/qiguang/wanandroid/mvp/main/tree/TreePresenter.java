package com.qiguang.wanandroid.mvp.main.tree;

import com.qiguang.wanandroid.bean.TreeBean;
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
 * @Date: 2018 下午10:30
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreePresenter extends BasePresenter<TreeContract.View> implements TreeContract.Presenter{
    public TreePresenter(TreeContract.View view) {
        super(view);
    }

    @Override
    public void requestTree() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getTree()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<TreeBean>(view){

                    @Override
                    public void onSuccess(TreeBean treeBean) {
                        view.setupTree(treeBean);
                    }
                }));
    }

    @Override
    public void refresh() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .getTree()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<TreeBean>(view){
                    @Override
                    public void onSuccess(TreeBean treeBean) {
                        view.refresh(treeBean);
                    }
                }));
    }

    @Override
    public void loadMore() {

    }
}
