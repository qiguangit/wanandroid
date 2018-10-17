package com.qiguang.wanandroid.mvp.main.tree;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.SpacesItemDecoration;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseViewPagerFragment;
import com.qiguang.wanandroid.bean.TreeBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.common_net_address.CommonNetAddressActivity;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.mvp.search.SearchActivity;
import com.qiguang.wanandroid.mvp.treedetail.TreeDetailActivity;
import com.qiguang.wanandroid.view.LoadingPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:37
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeFragment extends BaseViewPagerFragment<TreePresenter> implements TreeContract.View{
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
    private TreeAdapter mAdapter;

    @Override
    public boolean setLazy() {
        return true;
    }

    @Override
    protected TreePresenter createPresenter() {
        return new TreePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_tree;

    }

    @Override
    protected void initData() {
        setupToolbar();
        ivGotoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewHome.scrollToPosition(0);
            }
        });
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerViewHome.addItemDecoration(new SpacesItemDecoration((int) context.getResources().getDimension(R.dimen.qb_px_8)));
        presenter.requestTree();
    }

    @Override
    public void setupTree(final TreeBean treeBean) {
        mAdapter = new TreeAdapter(R.layout.item_tree,treeBean.getData());
        recyclerViewHome.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(context, TreeDetailActivity.class);
                intent.putExtra(Constant.DATA,treeBean.getData().get(position));
                context.startActivity(intent);
            }
        });
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    private void setupToolbar() {
        tvTitle.setText("知识体系");
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

    @Override
    public void refresh(TreeBean treeBean) {
        mAdapter.setNewData(treeBean.getData());
    }

    @Override
    public void loadMore(TreeBean treeBean) {
        refreshLayout.setNoMoreData(true);
        refreshLayout.finishLoadMore(3000);
    }

}
