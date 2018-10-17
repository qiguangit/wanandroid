package com.qiguang.wanandroid.mvp.common_net_address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.CommonNetAddressBean;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.detail.DetailActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午4:20
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CommonNetAddressActivity extends BaseActivity<CommonNetAddressPresenter> implements CommonNetAddressContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tag_flow_layout)
    TagFlowLayout tagFlowLayout;


    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_common_net_address;
    }

    @Override
    protected CommonNetAddressPresenter createPresenter() {
        return new CommonNetAddressPresenter(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
        presenter.requestCommonNetAddress();
    }

    @Override
    public void setupCommonNetAddress(final CommonNetAddressBean bean) {
        tagFlowLayout.setAdapter(new CommonNetAddressTagAdapter(bean.getData()));
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                CommonNetAddressBean.DataBean dataBean = bean.getData().get(position);
                Intent intent=new Intent(CommonNetAddressActivity.this, DetailActivity.class);
                DetailBean detailBean=new DetailBean(dataBean.getName(),dataBean.getLink(),false,"",dataBean.getId());
                intent.putExtra(Constant.DATA,detailBean);
                startActivity(intent);
                return false;
            }
        });
    }


}
