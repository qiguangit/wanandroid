package com.qiguang.wanandroid.mvp.search;

import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.HistoryBean;
import com.qiguang.wanandroid.bean.HotSearchBean;
import com.qiguang.wanandroid.db.DbHandler;
import com.qiguang.wanandroid.mvp.BooleanObserver;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.ListObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午6:26
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    int page = 0;

    public SearchPresenter(SearchContract.View view) {
        super(view);
    }

    @Override
    public void requestHotSearch() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestHotSearch()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<HotSearchBean>(view, "搜索发生错误") {
                    @Override
                    public void onSuccess(HotSearchBean bean) {
                        view.setupHotSearch(bean);
                    }
                }));
    }

    @Override
    public void search(final String key) {
        addHistory(key);
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .searchArticleByKey(page, key)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<ArticleBean>(view, "搜索失败") {
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        view.gotoSearchDetail(key, articleBean);
                    }
                }));
    }

    @Override
    public void queryHistory() {
        addSubscribe(DbHandler.getInstance()
                .queryAllRecord()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new ListObserver<HistoryBean>(view) {
                    @Override
                    public void onSuccess(List<HistoryBean> list) {
                        view.setupHistory((ArrayList<HistoryBean>) list);
                    }
                }));
    }

    @Override
    public void addHistory(final String key) {
        addSubscribe(DbHandler.getInstance().findRecord(key)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BooleanObserver() {
                    @Override
                    public void onNext(Boolean isExits) {
                        if (!isExits) {
                            addRecord(key);
                        } else {
                            changeTimestamp(key);
                        }
                    }
                }));
    }

    @Override
    public void removeHistory(String key) {
        addSubscribe(DbHandler.getInstance()
                .removeRecord(key)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BooleanObserver()));

    }

    @Override
    public void removeAllHistory() {
        addSubscribe(DbHandler.getInstance().removeAllRecord()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BooleanObserver() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        queryHistory();
                    }
                }));

    }

    @Override
    public void refreshHistory() {
        addSubscribe(DbHandler.getInstance()
                .queryAllRecord()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new ListObserver<HistoryBean>(view) {
                    @Override
                    public void onSuccess(List<HistoryBean> list) {
                        if (list != null && list.size() > 0) {
                            view.setupHistory((ArrayList<HistoryBean>) list);
                        } else {
                            view.showEmptyHistory();
                        }
                    }
                }));
    }

    @Override
    public void addRecord(String key) {
        addSubscribe(DbHandler.getInstance().addRecord(key)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BooleanObserver()));
    }

    @Override
    public void changeTimestamp(String key) {
        addSubscribe(DbHandler.getInstance().changeTimestamp(key)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BooleanObserver()));

    }
}
