package com.qiguang.wanandroid.mvp.main.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.SpacesItemDecoration;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseViewPagerFragment;
import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.BannerBean;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.common_net_address.CommonNetAddressActivity;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.mvp.search.SearchActivity;
import com.qiguang.wanandroid.view.GlideImageLoader;
import com.qiguang.wanandroid.view.LoadingPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:34
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class HomeFragment extends BaseViewPagerFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerViewHome;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_goto_top)
    ImageView ivGotoTop;
    private Banner mBanner;
    ArrayList<String> urls = new ArrayList<>();
    private HomeAdapter mAdapter;

    @Override
    public boolean setLazy() {
        return true;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        setupRefresh();
        setupToolbar();
        recyclerViewHome.addItemDecoration(new SpacesItemDecoration((int) context.getResources().getDimension(R.dimen.qb_px_10)));
        presenter.requestArticles();

        ivGotoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewHome.smoothScrollToPosition(0);
            }
        });
    }

    private void setupRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore();
                refreshLayout.finishLoadMore();
            }
        });
    }


    @Override
    public void setupArticles(ArticleBean articleBean) {
        final List<ArticleBean.DataBean.DatasBean> datas = articleBean.getData().getDatas();
        mAdapter = new HomeAdapter(R.layout.item_home,datas);
        recyclerViewHome.setAdapter(mAdapter);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DataBean.DatasBean bean = datas.get(position);
                startActivity(new DetailBean(bean.getTitle(),bean.getLink(),false,bean.getDesc(),bean.getId()));
            }
        });
    }


    @Override
    public void setupBanner(final BannerBean bannerBean) {
        final ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mBanner = view.findViewById(R.id.banner);
        view.removeView(mBanner);
        mBanner.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.qb_px_150)));
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.CubeOut);
        mBanner.setDelayTime(4000);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean.DataBean bean = bannerBean.getData().get(position);
                startActivity(new DetailBean(bean.getTitle(),bean.getUrl(),false,bean.getDesc(),bean.getId()));
            }
        });
        List<BannerBean.DataBean> data = bannerBean.getData();
        ArrayList<String> paths = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for (BannerBean.DataBean item : data) {
            paths.add(item.getImagePath());
            titles.add(item.getTitle());
            urls.add(item.getUrl());
        }
        mBanner.setBannerTitles(titles);
        mBanner.setImages(paths);
        mAdapter.addHeaderView(mBanner,0);
        mBanner.start();
        recyclerViewHome.scrollToPosition(0);
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);

    }

    private void startActivity(DetailBean bean){
        Intent intent=new Intent(context, DetailActivity.class);
        intent.putExtra("data",bean);
        context.startActivity(intent);
    }

    @Override
    public void refresh(ArticleBean articleBean) {
        mAdapter.setNewData(articleBean.getData().getDatas());
    }

    @Override
    public void loadMore(ArticleBean articleBean) {
        mAdapter.addData(articleBean.getData().getDatas());
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.nav);
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).switchNavigation(true);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.website){
                    Intent intent=new Intent(context, CommonNetAddressActivity.class);
                    context.startActivity(intent);
                }else if(item.getItemId()==R.id.search){
                    Intent intent=new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                }
                return false;
            }
        });
    }

}
