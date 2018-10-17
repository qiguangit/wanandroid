package com.qiguang.wanandroid.mvp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseShowHideFragment;
import com.qiguang.wanandroid.common.AppManager;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.event.SwitchNightEvent;
import com.qiguang.wanandroid.mvp.common_net_address.CommonNetAddressActivity;
import com.qiguang.wanandroid.mvp.feedback.FeedbackActivity;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.mvp.search.SearchActivity;
import com.qiguang.wanandroid.view.LoadingPager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-25 下午3:38
 * @Description: 设置页面
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SettingFragment extends BaseShowHideFragment<SettingContract.Presenter> implements SettingContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.ll_clean_cache)
    LinearLayout llCleanCache;
    @BindView(R.id.switch_auto_cache)
    Switch switchAutoCache;
    @BindView(R.id.switch_night_mode)
    Switch switchNightMode;
    @BindView(R.id.switch_push)
    Switch switchPush;
    private SPUtils mUtils;

    @Override
    public boolean setLazy() {
        return true;
    }

    @Override
    protected SettingContract.Presenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        setupToolbar();
        mUtils = SPUtils.getInstance();
        switchAutoCache.setChecked(mUtils.getBoolean(Constant.Setting.IS_AUTO_CACHE));
        switchNightMode.setChecked(mUtils.getBoolean(Constant.Setting.IS_NIGHT_MODE));
        switchPush.setChecked(mUtils.getBoolean(Constant.Setting.IS_NO_PUSH)==false?true:false);
        tvCacheSize.setText(FileUtils.getDirSize(PathUtils.getInternalAppCachePath()));
    }

    @Override
    public void switchAutoCache(Boolean isAutoCache) {
        //        cbAutoCache.setChecked(isAutoCache);
    }

    @Override
    public void switchNightMode(boolean isNightMode) {
        //        cbNightMode.setChecked(isNightMode);
    }

    @Override
    public void setupCacheSize(String cache) {

    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.nav);
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) context).switchNavigation(true));
        tvTitle.setText("设置");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.website) {
                    Intent intent = new Intent(context, CommonNetAddressActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.search) {
                    Intent intent = new Intent(context, SearchActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    @OnClick({R.id.ll_feedback, R.id.ll_clean_cache, R.id.switch_auto_cache,
            R.id.switch_night_mode, R.id.switch_push})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_feedback:
                Intent intent = new Intent(context, FeedbackActivity.class);
                context.startActivity(intent);
                break;
            case R.id.ll_clean_cache:
                CleanUtils.cleanInternalCache();
                tvCacheSize.setText(FileUtils.getDirSize(PathUtils.getInternalAppCachePath()));
                break;
            case R.id.switch_auto_cache:
                mUtils.put(Constant.Setting.IS_AUTO_CACHE, switchAutoCache.isChecked());
                break;
            case R.id.switch_night_mode:
                mUtils.put(Constant.Setting.IS_NIGHT_MODE, switchNightMode.isChecked());
                AppCompatDelegate.setDefaultNightMode(switchNightMode.isChecked() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                EventBus.getDefault().post(new SwitchNightEvent());
                AppManager.getInstance().removeStack(context);
                context.recreate();
                break;
            case R.id.switch_push:
                mUtils.put(Constant.Setting.IS_NO_PUSH,!switchPush.isChecked());
                break;
            default:
                break;
        }
    }

}
