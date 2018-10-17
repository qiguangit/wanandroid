package com.qiguang.wanandroid.mvp.collect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.SpacesItemDecoration;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseShowHideFragment;
import com.qiguang.wanandroid.bean.CollectListBean;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.view.LoadingPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-23 下午6:18
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CollectListFragment extends BaseShowHideFragment<CollectListContract.Presenter> implements CollectListContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerViewHome;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_goto_top)
    ImageView ivGotoTop;
    private CollectListAdapter mAdapter;

    @Override
    public boolean setLazy() {
        //此fragment需要延迟加载
        return true;
    }

    @Override
    protected CollectListContract.Presenter createPresenter() {
        return new CollectListPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_collcet;
    }

    @Override
    protected void initData() {
        tvTitle.setText("收藏");
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) context).switchNavigation(true));
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
        ivGotoTop.setOnClickListener(v -> recyclerViewHome.smoothScrollToPosition(0));
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewHome.addItemDecoration(new SpacesItemDecoration((int) context.getResources().getDimension(R.dimen.qb_px_10)));
        presenter.requestCollectList();
    }

    @Override
    public void setupCollectList(CollectListBean bean) {
        mAdapter = new CollectListAdapter(R.layout.item_collectlist, bean.getData().getDatas());
        recyclerViewHome.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CollectListBean.DataBean.DatasBean datasBean = bean.getData().getDatas().get(position);
            DetailBean detailBean = new DetailBean(datasBean.getTitle(), datasBean.getLink(), true, datasBean.getDesc(), datasBean.getId());
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Constant.DATA, detailBean);
            context.startActivity(intent);
        });
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    @Override
    public void refresh(CollectListBean bean) {
        mAdapter.setNewData(bean.getData().getDatas());
    }

    @Override
    public void loadMore(CollectListBean bean) {
        mAdapter.addData(bean.getData().getDatas());
    }
}
