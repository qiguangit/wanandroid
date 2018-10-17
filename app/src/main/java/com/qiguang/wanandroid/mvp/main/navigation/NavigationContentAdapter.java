package com.qiguang.wanandroid.mvp.main.navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.App;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.bean.NavigationBean;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Random;


/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-12 下午10:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class NavigationContentAdapter extends BaseQuickAdapter<NavigationBean.DataBean, NavigationContentViewHolder> {
    public NavigationContentAdapter(int layoutResId, @Nullable List<NavigationBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final NavigationContentViewHolder helper, final NavigationBean.DataBean item) {
        helper.itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT));
        helper.tvTitle.setText(item.getName());

        helper.tagFlowLayout.setAdapter(new TagAdapter<NavigationBean.DataBean.ArticlesBean>(item.getArticles()) {
            @Override
            public View getView(FlowLayout parent, int position, NavigationBean.DataBean.ArticlesBean articlesBeans) {
                TextView tv = (TextView) LayoutInflater.from(App.getContext()).inflate(R.layout.flow_layout_tv, null);
                tv.setText(articlesBeans.getTitle());
                int r = new Random().nextInt(255);
                int g = new Random().nextInt(255);
                int b = new Random().nextInt(255);

                tv.setTextColor(Color.argb(255, r, g, b));
                return tv;
            }
        });
        helper.tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                NavigationBean.DataBean.ArticlesBean articlesBean = item.getArticles().get(position);
                DetailBean detailBean = new DetailBean(articlesBean.getTitle(), articlesBean.getLink(), articlesBean.isCollect(), articlesBean.getDesc(), articlesBean.getId());
                Intent intent = new Intent(App.getContext(), DetailActivity.class);
                intent.putExtra("data", detailBean);
                helper.itemView.getContext().startActivity(intent);
                return false;
            }
        });
    }

}
