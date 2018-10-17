package com.qiguang.wanandroid.mvp.setting;

import com.qiguang.wanandroid.mvp.BasePresenter;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-25 下午3:56
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter{
    public SettingPresenter(SettingContract.View view) {
        super(view);
    }

    @Override
    public void switchAutoCache(boolean isAutoCache) {

    }

    @Override
    public void switchNightMode(boolean isNightMode) {

    }

    @Override
    public void feedback() {

    }

    @Override
    public void cleanCache() {

    }

    @Override
    public void queryCacheSize() {

    }
}
