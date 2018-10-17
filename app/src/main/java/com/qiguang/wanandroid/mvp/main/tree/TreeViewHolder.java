package com.qiguang.wanandroid.mvp.main.tree;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:26
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeViewHolder extends BaseViewHolder {
    TextView tvTitle;
    TextView tvContent;
    public TreeViewHolder(View view) {
        super(view);
        findViews(view);
    }
    private void findViews(View itemView) {
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }
}
