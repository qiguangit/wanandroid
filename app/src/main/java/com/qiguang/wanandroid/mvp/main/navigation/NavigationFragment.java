package com.qiguang.wanandroid.mvp.main.navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.App;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.OnItemClickListener;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseViewPagerFragment;
import com.qiguang.wanandroid.bean.NavigationBean;
import com.qiguang.wanandroid.mvp.common_net_address.CommonNetAddressActivity;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.mvp.search.SearchActivity;
import com.qiguang.wanandroid.view.LoadingPager;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-12 下午10:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class NavigationFragment extends BaseViewPagerFragment<NavigationContract.Presenter> implements NavigationContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recycler_view_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.iv_goto_top)
    ImageView ivGotoTop;
    private LinearLayoutManager mContentLayoutManager;
    private LinearLayoutManager mTitleLayoutManager;
    private boolean move = false;
    /**
     * 此字段标记是否是title单击产生的滑动
     */
    private boolean isUserClickTitle = false;
    private int mIndex;
    private NavigationTitleAdapter mTitleAdapter;

    @Override
    public boolean setLazy() {
        return true;
    }

    @Override
    protected NavigationContract.Presenter createPresenter() {
        return new NavigationPresenter(this);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initData() {

        setupToolbar();
        ivGotoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewTitle.smoothScrollToPosition(0);
                recyclerViewContent.smoothScrollToPosition(0);
            }
        });
        presenter.requestNavigation();
    }

    @Override
    public void setupNavigation(NavigationBean bean) {
        setupTitle(bean);
        setupContent(bean);
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    private void setupContent(NavigationBean bean) {
        recyclerViewContent.setAdapter(new NavigationContentAdapter(R.layout.item_nav_content, bean.getData()));
        mContentLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewContent.setLayoutManager(mContentLayoutManager);
        recyclerViewContent.addOnScrollListener(new RecyclerViewListener());
    }

    private void setupTitle(NavigationBean bean) {
        mTitleAdapter = new NavigationTitleAdapter(R.layout.item_nav_title, bean.getData());
        mTitleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int firstItem = mContentLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mContentLayoutManager.findLastVisibleItemPosition();
                if (position <= firstItem) {
                    recyclerViewContent.smoothScrollToPosition(position);
                } else if (position <= lastItem) {
                    int top = recyclerViewContent.getChildAt(position - firstItem).getTop();
                    recyclerViewContent.smoothScrollBy(0, top);
                } else {
                    recyclerViewContent.smoothScrollToPosition(position);
                    move = true;
                }
                recyclerViewTitle.smoothScrollToPosition(position);
                mTitleAdapter.setCurrentPosition(position);
                mIndex = position;
                isUserClickTitle = true;
            }
        });

        recyclerViewTitle.setAdapter(mTitleAdapter);
        mTitleLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewTitle.setLayoutManager(mTitleLayoutManager);
    }

    private void setupToolbar() {
        tvTitle.setText("导航");
        toolbar.setNavigationIcon(R.drawable.nav);
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).switchNavigation(true);
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


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                if (move) {
                    move = false;
                    int n = mIndex - mContentLayoutManager.findFirstVisibleItemPosition();
                    Log.d("n---->", String.valueOf(n));
                    if (0 <= n && n < recyclerViewContent.getChildCount()) {
                        int top = recyclerViewContent.getChildAt(n).getTop();
                        Log.d("top--->", String.valueOf(top));
                        recyclerViewContent.smoothScrollBy(0, top);
                    }
                }
                if (!isUserClickTitle) {
                    int i = mContentLayoutManager.findFirstVisibleItemPosition();
                    recyclerViewTitle.smoothScrollToPosition(i);
                    mTitleAdapter.setCurrentPosition(i);
                }
                isUserClickTitle = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
