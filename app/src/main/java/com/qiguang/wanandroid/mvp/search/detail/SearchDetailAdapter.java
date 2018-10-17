package com.qiguang.wanandroid.mvp.search.detail;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.bean.ArticleBean;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午8:01
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
class SearchDetailAdapter extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean,SearchDetailViewHolder> {

    public SearchDetailAdapter(int layoutResId, @Nullable List<ArticleBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SearchDetailViewHolder helper, ArticleBean.DataBean.DatasBean item) {
        helper.tvAuthor.setText(item.getAuthor());
        helper.superChapter.setText(item.getSuperChapterName());
        helper.chapter.setText(item.getChapterName());
        helper.tvTitle.setText(item.getTitle());
        helper.tvTime.setText(item.getNiceDate());
    }
}
