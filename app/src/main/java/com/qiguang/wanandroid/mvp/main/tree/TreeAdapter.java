package com.qiguang.wanandroid.mvp.main.tree;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.OnItemClickListener;
import com.qiguang.wanandroid.bean.TreeBean;
import com.qiguang.wanandroid.mvp.treedetail.TreeDetailActivity;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午3:01
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeAdapter extends BaseQuickAdapter<TreeBean.DataBean, TreeViewHolder> {
    public TreeAdapter(int layoutResId, @Nullable List<TreeBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(TreeViewHolder helper, TreeBean.DataBean item) {
        helper.tvTitle.setText(item.getName());
        StringBuilder sb = new StringBuilder();
        for (TreeBean.DataBean.ChildrenBean itemData : item.getChildren()) {
            sb.append(itemData.getName() + "  ");
        }
        helper.tvContent.setText(sb.toString());
    }

}
