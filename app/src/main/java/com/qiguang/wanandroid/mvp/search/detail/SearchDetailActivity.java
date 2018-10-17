package com.qiguang.wanandroid.mvp.search.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.SpacesItemDecoration;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午2:32
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SearchDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_article)
    RecyclerView recyclerViewArticle;
    @BindView(R.id.iv_goto_top)
    ImageView ivGotoTop;
    private ArticleBean mBean;
    private String mKey;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_search_detail;
    }

    @Override
    protected void initData() {
        mBean = (ArticleBean) getIntent().getSerializableExtra(Constant.DATA);
        mKey = getIntent().getStringExtra(Constant.KEY);
        if(mBean==null||mBean.getErrorCode()!=0){
            Toast.makeText(this, "出现未知错误，即将退出当前页面", Toast.LENGTH_SHORT).show();
            remove();
        }else {
            tvTitle.setText(mKey);
            toolbar.setNavigationIcon(R.drawable.ic_back_gray);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove();
                }
            });
            ivGotoTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewArticle.smoothScrollToPosition(0);
                }
            });
            SearchDetailAdapter adapter = new SearchDetailAdapter(R.layout.item_home, mBean.getData().getDatas());
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ArticleBean.DataBean.DatasBean datasBean = mBean.getData().getDatas().get(position);
                    DetailBean detailBean=new DetailBean(datasBean.getTitle(),datasBean.getLink(),datasBean.isCollect(),datasBean.getDesc(),datasBean.getId());
                    Intent intent=new Intent(SearchDetailActivity.this, DetailActivity.class);
                    intent.putExtra(Constant.DATA,detailBean);
                    startActivity(intent);
                }
            });
            recyclerViewArticle.setAdapter(adapter);
            recyclerViewArticle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            recyclerViewArticle.addItemDecoration(new SpacesItemDecoration((int) getResources().getDimension(R.dimen.qb_px_10)));
        }
    }

}
