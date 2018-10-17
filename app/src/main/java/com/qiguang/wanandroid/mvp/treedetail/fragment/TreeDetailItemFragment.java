package com.qiguang.wanandroid.mvp.treedetail.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.SpacesItemDecoration;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseViewPagerFragment;
import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;
import com.qiguang.wanandroid.view.LoadingPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午4:27
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeDetailItemFragment extends BaseViewPagerFragment<TreeDetailItemContract.Presenter> implements TreeDetailItemContract.View {

    @BindView(R.id.recycler_view_tree_detail)
    RecyclerView recyclerViewTreeDetail;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int page;
    private int id;
    private TreeDetailAdapter mAdapter;

    @Override
    public boolean setLazy() {
        return false;
    }

    @Override
    protected TreeDetailItemPresenter createPresenter() {
        return new TreeDetailItemPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_tree_detail;
    }

    @Override
    protected void initData() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore(page,id);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh(page,id);
            }
        });
        LogUtils.d("page:"+page+" id:"+id);
        presenter.requestArticle(page, id);
    }

    public void scrollToTop(){
        recyclerViewTreeDetail.smoothScrollToPosition(0);
    }

    public void setData(int page, int id) {
        this.page = page;
        this.id = id;
    }

    @Override
    public void setupArticle(final ArticleBean bean) {
        mAdapter = new TreeDetailAdapter(R.layout.item_tree_detail,bean.getData().getDatas());
        recyclerViewTreeDetail.setAdapter(mAdapter);
        recyclerViewTreeDetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewTreeDetail.addItemDecoration(new SpacesItemDecoration((int) context.getResources().getDimension(R.dimen.qb_px_10)));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DataBean.DatasBean datasBean = bean.getData().getDatas().get(position);
                Intent intent=new Intent(context, DetailActivity.class);
                DetailBean detailBean = new DetailBean(datasBean.getTitle(), datasBean.getLink(), datasBean.isCollect(), datasBean.getDesc(), datasBean.getId());
                intent.putExtra(Constant.DATA,detailBean);
                context.startActivity(intent);
            }
        });
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    @Override
    public void refresh(ArticleBean bean) {
        mAdapter.setNewData(bean.getData().getDatas());
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void loadMore(ArticleBean bean) {
        mRefreshLayout.setNoMoreData(true);
        mRefreshLayout.finishLoadMore(3000);
    }

}
