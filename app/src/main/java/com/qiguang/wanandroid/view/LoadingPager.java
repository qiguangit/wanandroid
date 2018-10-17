package com.qiguang.wanandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.utils.UIUtils;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午12:51
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class LoadingPager extends FrameLayout {
    private Context context;

    /**
     * 4种状态
     * 无网络的情况下
     */
    public static final int STATE_EMPTY = 0;
    /**
     * 加载失败
     */
    public static final int STATE_ERROR = 1;
    /**
     * 加载种
     */
    public static final int STATE_LOADING = 2;
    /**
     * 加载成功
     */
    public static final int STATE_SUCCESS = 3;

    private int currentState = STATE_LOADING;

    /**
     * 四种界面
     */
    private View viewEmpty;
    private View viewError;
    private View viewLoading;
    private View viewSuccess;

    private LayoutParams mParams;

    public LoadingPager(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化布局
        initPager();
    }

    private void initPager() {
        mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (viewEmpty == null) {
            viewEmpty = LayoutInflater.from(context).inflate(R.layout.view_empty, null);
            addView(viewEmpty, mParams);
        }
        if (viewError == null) {
            viewError = LayoutInflater.from(context).inflate(R.layout.view_error, null);
            addView(viewError, mParams);
        }
        if (viewLoading == null) {
            viewLoading = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
            addView(viewLoading, mParams);
        }
        //根据状态显示不同的页面
        showSafePager();
    }

    public int getCurrentState() {
        return currentState;
    }

    /**
     * 安全的显示布局，确保其运行在主线程中
     */
    private void showSafePager() {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                showPager();
            }
        });
    }

    /**
     * 根据状态显示不同的布局
     */
    private void showPager() {
        viewEmpty.setVisibility(currentState == STATE_EMPTY ? VISIBLE : INVISIBLE);
        viewError.setVisibility(currentState == STATE_ERROR ? VISIBLE : INVISIBLE);
        viewLoading.setVisibility(currentState == STATE_LOADING ? VISIBLE : INVISIBLE);

        if (viewSuccess == null) {
            viewSuccess = LayoutInflater.from(context).inflate(successLayoutId(), null);
            addView(viewSuccess, mParams);
        }
        viewSuccess.setVisibility(currentState == STATE_SUCCESS ? VISIBLE : INVISIBLE);
    }

    /**
     * 加载成功的布局Id
     *
     * @return
     */
    public abstract int successLayoutId();

    public void changeState(int state) {
        currentState = state;
        showSafePager();
    }
}
