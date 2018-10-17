package com.qiguang.wanandroid.mvp.main.navigation;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-12 下午10:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class NavigationContentViewHolder extends BaseViewHolder {
    TextView tvTitle;
    TagFlowLayout tagFlowLayout;

    public NavigationContentViewHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View itemView) {
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tagFlowLayout = (TagFlowLayout) itemView.findViewById(R.id.tag_flow_layout);
    }
}
