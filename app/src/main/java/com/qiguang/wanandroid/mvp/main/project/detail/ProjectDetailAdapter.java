package com.qiguang.wanandroid.mvp.main.project.detail;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.App;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.bean.ProjectDetailBean;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午1:31
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
class ProjectDetailAdapter extends BaseQuickAdapter<ProjectDetailBean.DataBean.DatasBean,ProjectDetailViewHolder>{
    public ProjectDetailAdapter(int layoutResId, @Nullable List<ProjectDetailBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProjectDetailViewHolder helper, ProjectDetailBean.DataBean.DatasBean item) {
        Glide.with(App.getContext())
                .load(item.getEnvelopePic())
                .into(helper.ivThumbnail);
        helper.tvAuthor.setText(item.getAuthor());
        helper.tvDesc.setText(item.getDesc());
        helper.tvTime.setText(item.getNiceDate());
        helper.tvTitle.setText(item.getTitle());
    }
}
