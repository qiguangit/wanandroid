package com.qiguang.wanandroid.mvp.treedetail.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:30
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeDetailViewHolder extends BaseViewHolder {

    ImageView ivIcon;
    TextView tvAuthor;
    TextView superChapter;
    TextView chapter;
    TextView tvTitle;
    ImageView ivCollection;
    ImageView ivTime;
    TextView tvTime;
    public TreeDetailViewHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View itemView) {
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        superChapter = (TextView) itemView.findViewById(R.id.super_chapter);
        chapter = (TextView) itemView.findViewById(R.id.chapter);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        ivCollection = (ImageView) itemView.findViewById(R.id.iv_collection);
        ivTime = (ImageView) itemView.findViewById(R.id.iv_time);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
    }
}
