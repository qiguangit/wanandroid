package com.qiguang.wanandroid.mvp.search;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午1:27
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
class HistoryAdapter extends BaseQuickAdapter<HistoryBean,HistoryViewHolder> {

    public HistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HistoryViewHolder helper, HistoryBean item) {
        helper.tvHistory.setText(item.getKey());
    }
}
