package com.qiguang.wanandroid.mvp.common_net_address;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.bean.CommonNetAddressBean;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午4:09
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CommonNetAddressAdapter extends BaseQuickAdapter<CommonNetAddressBean.DataBean,CommonNetAddressViewHolder> {
    public CommonNetAddressAdapter(int layoutResId, @Nullable List<CommonNetAddressBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(CommonNetAddressViewHolder helper, CommonNetAddressBean.DataBean item) {

    }
}
