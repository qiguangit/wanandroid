package com.qiguang.wanandroid.mvp.collect;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-25 下午3:09
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
class CollectListViewHolder extends BaseViewHolder {
    ImageView ivIcon;
    TextView tvAuthor;
    TextView chapter;
    TextView tvTitle;
    ImageView ivCollection;
    ImageView ivTime;
    TextView tvTime;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-09-25 15:13:21 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View itemView) {
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        chapter = (TextView) itemView.findViewById(R.id.chapter);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        ivCollection = (ImageView) itemView.findViewById(R.id.iv_collection);
        ivTime = (ImageView) itemView.findViewById(R.id.iv_time);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
    }

    public CollectListViewHolder(View view) {
        super(view);
        findViews(view);
    }
}
