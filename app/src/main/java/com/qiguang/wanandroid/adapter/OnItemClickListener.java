package com.qiguang.wanandroid.adapter;

import android.view.View;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:31
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface OnItemClickListener {
    /**
     * item点击事件回调
     * @param itemView
     * @param position
     */
    void onItemClick(View itemView, int position);
}
