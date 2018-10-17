package com.qiguang.wanandroid.mvp.treedetail.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.OnItemClickListener;
import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午9:44
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeDetailAdapter extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean,TreeDetailViewHolder> {
    public TreeDetailAdapter(int layoutResId, @Nullable List<ArticleBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(TreeDetailViewHolder helper, ArticleBean.DataBean.DatasBean item) {
        helper.tvAuthor.setText(item.getAuthor());
        helper.superChapter.setText(item.getSuperChapterName());
        helper.chapter.setText(item.getChapterName());
        helper.tvTitle.setText(item.getTitle());
        helper.tvTime.setText(item.getNiceDate());
    }
}
