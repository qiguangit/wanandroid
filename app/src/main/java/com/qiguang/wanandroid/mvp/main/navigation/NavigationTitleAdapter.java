package com.qiguang.wanandroid.mvp.main.navigation;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ResourceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.bean.NavigationBean;
import com.qiguang.wanandroid.utils.UIUtils;

import java.util.List;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-12 下午10:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class NavigationTitleAdapter extends BaseQuickAdapter<NavigationBean.DataBean, NavigationTitleViewHolder> {
    private int selectedPosition = 0;

    public NavigationTitleAdapter(int layoutResId, @Nullable List<NavigationBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(NavigationTitleViewHolder helper, NavigationBean.DataBean item) {
        helper.tvTitle.setText(item.getName());
        if (selectedPosition == helper.getLayoutPosition()) {
            helper.itemView.setBackgroundColor(UIUtils.getColor(R.color.navTitleChecked));
        } else {
            helper.itemView.setBackgroundColor(UIUtils.getColor(R.color.navTitleUnChecked));
        }
    }

    public void setCurrentPosition(int position) {
        int temp = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(temp);
        notifyItemChanged(selectedPosition);
    }

}
