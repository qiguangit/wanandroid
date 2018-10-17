package com.qiguang.wanandroid.mvp.search;

import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.HistoryBean;
import com.qiguang.wanandroid.bean.HotSearchBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

import java.util.ArrayList;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午6:14
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface SearchContract {
    interface Presenter extends IPresenter{
        /**
         * 请求热搜
         */
        void requestHotSearch();

        /**
         * 搜索
         * @param key
         */
        void search(String key);

        /**
         * 查询历史搜索信息
         */
        void queryHistory();

        /**
         * 添加历史数据
         * @param key
         */
        void addHistory(String key);

        /**
         * 删除历史数据
         * @param key
         */
        void removeHistory(String key);

        /**
         * 删除所有历史记录
         */
        void removeAllHistory();


        /**
         * 刷新历史记录
         */
        void refreshHistory();

        /**
         * 添加记录
         * @param key
         */
        void addRecord(String key);

        /**
         * 更新搜索时间
         * @param key
         */
        void changeTimestamp(String key);
    }

    interface View extends IView{
        /**
         * 设置热搜数据
         * @param bean
         */
        void setupHotSearch(HotSearchBean bean);

        /**
         * 设置搜索历史
         * @param bean
         */
        void setupHistory(ArrayList<HistoryBean> bean);

        /**
         * 跳转到搜索详情页
         * @param bean
         * @param key
         */
        void gotoSearchDetail(String key,ArticleBean bean);

        /**
         * 显示空的历史数据
         */
        void showEmptyHistory();

        /**
         * 刷新历史记录
         * @param bean
         */
        void refreshHistory(ArrayList<HistoryBean> bean);
    }
}
