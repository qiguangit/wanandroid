package com.qiguang.wanandroid.mvp.main.navigation;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午9:45
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class NavigationTitleViewHolder extends BaseViewHolder {
    TextView tvTitle;
    View itemView;

    public NavigationTitleViewHolder(View view) {
        super(view);
        this.itemView=view;
        findViews(view);
    }

    private void findViews(View itemView) {
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }
}
