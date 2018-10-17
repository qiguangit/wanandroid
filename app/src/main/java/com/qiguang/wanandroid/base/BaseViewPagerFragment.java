package com.qiguang.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.qiguang.wanandroid.mvp.IPresenter;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-15 上午10:30
 * @Description: 添加到viewPager中的Fragment 皆继承自改抽象类
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class BaseViewPagerFragment<P extends IPresenter> extends BaseFragment {
    public P presenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            JAnalyticsInterface.onPageStart(context,getClass().getSimpleName());
        }else {
            JAnalyticsInterface.onPageEnd(context,getClass().getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null) {
            presenter.attach();
        }
    }

    /**
     * 返回mvp presenter层
     *
     * @return
     */
    @Override
    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
