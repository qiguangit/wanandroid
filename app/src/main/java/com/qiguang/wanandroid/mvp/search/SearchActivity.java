package com.qiguang.wanandroid.mvp.search;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.ArticleBean;
import com.qiguang.wanandroid.bean.HistoryBean;
import com.qiguang.wanandroid.bean.HotSearchBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.search.detail.SearchDetailActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午6:08
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.tag_flow_layout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recycler_view_history)
    RecyclerView recyclerViewHistory;
    private HistoryAdapter mAdapter;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        Drawable icSearch = getResources().getDrawable(R.drawable.ic_search_1, null);
        int dp15 = (int) getResources().getDimension(R.dimen.qb_px_15);
        icSearch.setBounds(0, 0, dp15, dp15);
        etSearch.setCompoundDrawables(icSearch, null, null, null);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.requestHotSearch();
        presenter.queryHistory();
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_search, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                remove();
                break;
            case R.id.btn_search:
                String key = etSearch.getText().toString();
                if (!TextUtils.isEmpty(key)) {
                    presenter.search(key);
                } else {
                    Toast.makeText(this, "搜索的关键字不能为空哦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_clear:
                presenter.removeAllHistory();
                break;
            default:
                break;
        }
    }

    @Override
    public void setupHotSearch(final HotSearchBean bean) {
        tagFlowLayout.setAdapter(new SearchTagAdapter(bean.getData()));
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                HotSearchBean.DataBean dataBean = bean.getData().get(position);
                presenter.search(dataBean.getName());
                return false;
            }
        });
    }


    @Override
    public void setupHistory(final ArrayList<HistoryBean> bean) {
        tvEmpty.setVisibility(View.GONE);
        recyclerViewHistory.setVisibility(View.VISIBLE);
        mAdapter = new HistoryAdapter(R.layout.item_history, bean);
        recyclerViewHistory.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                presenter.search(bean.get(position).getKey());
            }
        });
    }

    @Override
    public void gotoSearchDetail(String key,ArticleBean bean) {
        Intent intent=new Intent(this,SearchDetailActivity.class);
        intent.putExtra(Constant.DATA,bean);
        intent.putExtra(Constant.KEY,key);
        startActivityForResult(intent,0);
    }

    @Override
    public void showError() {
        showEmptyHistory();
    }

    @Override
    public void showEmptyHistory() {
        tvEmpty.setVisibility(View.VISIBLE);
        recyclerViewHistory.setVisibility(View.GONE);
    }

    @Override
    public void refreshHistory(ArrayList<HistoryBean> bean) {
        mAdapter.setNewData(bean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.refreshHistory();
    }

}
