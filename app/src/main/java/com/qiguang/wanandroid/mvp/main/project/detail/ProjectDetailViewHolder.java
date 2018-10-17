package com.qiguang.wanandroid.mvp.main.project.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:18
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ProjectDetailViewHolder extends BaseViewHolder {
    ImageView ivThumbnail;
    ImageView ivIcon;
    TextView tvTitle;
    TextView tvDesc;
    TextView tvTime;
    TextView tvAuthor;
    public ProjectDetailViewHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View itemView) {
        ivThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
    }
}
