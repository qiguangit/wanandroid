package com.qiguang.wanandroid.mvp.main.project.detail;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.SpacesItemDecoration;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseViewPagerFragment;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.bean.ProjectDetailBean;
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
 * @Date: 18-9-9 下午12:32
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ProjectDetailFragment extends BaseViewPagerFragment<ProjectDetailContract.Presenter> implements ProjectDetailContract.View {

    @BindView(R.id.recycler_view_project_detail)
    RecyclerView recyclerViewProjectDetail;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page;
    private int cid;
    private ProjectDetailAdapter mAdapter;

    @Override
    public boolean setLazy() {
        return false;
    }

    @Override
    protected ProjectDetailContract.Presenter createPresenter() {
        return new ProjectDetailPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_project_detail;
    }

    public void setData(int page, int cid) {
        this.page = page;
        this.cid = cid;
    }

    public void gotoTop(){
        recyclerViewProjectDetail.smoothScrollToPosition(0);
    }

    @Override
    protected void initData() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh();
            }
        });
        presenter.setParams(page,cid);
        presenter.requestProjectDetail();
    }

    @Override
    public void setupProjectDetail(final ProjectDetailBean bean) {
        final ProjectDetailBean.DataBean data = bean.getData();
        mAdapter = new ProjectDetailAdapter(R.layout.item_project_detail,data.getDatas());
        recyclerViewProjectDetail.setAdapter(mAdapter);
        recyclerViewProjectDetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewProjectDetail.addItemDecoration(new SpacesItemDecoration((int) context.getResources().getDimension(R.dimen.qb_px_10)));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProjectDetailBean.DataBean.DatasBean datasBean = bean.getData().getDatas().get(position);
                Intent intent=new Intent(context, DetailActivity.class);
                DetailBean detailBean = new DetailBean(datasBean.getTitle(), datasBean.getLink(), datasBean.isCollect(), datasBean.getDesc(), datasBean.getId());
                intent.putExtra(Constant.DATA,detailBean);
                context.startActivity(intent);
            }
        });
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    @Override
    public void refresh(ProjectDetailBean bean) {
        mAdapter.setNewData(bean.getData().getDatas());
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadMore(ProjectDetailBean bean) {
        mAdapter.addData(bean.getData().getDatas());
        refreshLayout.finishLoadMore();
    }


}
