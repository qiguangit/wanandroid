package com.qiguang.wanandroid.base;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.qiguang.wanandroid.common.AppManager;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018-07-17 13:36
 * @Version: V1.0
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    private Unbinder mUnbinder;

    protected P presenter;

    private long[] mTimes = new long[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(layoutId());
        mUnbinder = ButterKnife.bind(this);

        AppManager.getInstance().add(this);
        presenter = createPresenter();
        initData();
    }

    /**
     * 是否全屏
     *
     * @return
     */
    protected abstract boolean isFullScreen();

    /**
     * 布局文件id
     *
     * @return
     */
    protected abstract int layoutId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected P createPresenter() {
        return null;
    }


    /**
     * 检查动态权限
     */
    protected void checkPermission(){

    }

    @Override
    public void showError() {

    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void remove() {
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this,getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this,getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (presenter != null) {
            presenter.detach();
        }
    }

    @Override
    public void onBackPressed() {
        AppManager appManager = AppManager.getInstance();
        if (appManager.isBottom()) {
            System.arraycopy(mTimes, 1, mTimes, 0, mTimes.length - 1);
            mTimes[mTimes.length - 1] = SystemClock.uptimeMillis();
            if (SystemClock.uptimeMillis() - mTimes[0] <= 1000) {
                //发生了双击事件
                appManager.removeActivity(this);
            }else {
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            }
        } else {
            appManager.removeActivity(this);
        }
    }
}
