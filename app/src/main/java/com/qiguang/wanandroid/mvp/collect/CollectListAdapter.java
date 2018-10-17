package com.qiguang.wanandroid.mvp.collect;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.bean.CollectListBean;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-25 下午3:07
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
class CollectListAdapter extends BaseQuickAdapter<CollectListBean.DataBean.DatasBean,CollectListViewHolder> {

    public CollectListAdapter(int layoutResId, @Nullable List<CollectListBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(CollectListViewHolder helper, CollectListBean.DataBean.DatasBean item) {
        helper.tvAuthor.setText(item.getAuthor());
        helper.chapter.setText(item.getChapterName());
        helper.tvTime.setText(item.getNiceDate());
        helper.tvTitle.setText(item.getTitle());
    }
}
