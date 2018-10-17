package com.qiguang.wanandroid.mvp.search;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午1:28
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
class HistoryViewHolder extends BaseViewHolder{
    TextView tvHistory;
    public HistoryViewHolder(View view) {
        super(view);
        tvHistory = view.findViewById(R.id.tv_history);
    }
}
