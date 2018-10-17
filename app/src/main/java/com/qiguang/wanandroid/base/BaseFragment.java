package com.qiguang.wanandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;
import com.qiguang.wanandroid.view.LoadingPager;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018-07-17 18:45
 * @Version: V1.0
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView{
    protected Unbinder mUnbinder;
    public FragmentActivity context;
    protected LoadingPagerImpl mLoadingPager;
    /**
     * 是否加载过数据
     */
    protected boolean isLoaded = false;
    /**
     * 是否需要懒加载
     */
    protected boolean isLazy = false;
    /**
     * 是否初始化(onCreateActivity是否被调用)
     */
    protected boolean isInited = false;


    /**
     * 选择当前页面是否懒加载
     *
     * @return
     */
    public abstract boolean setLazy();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

//        Log.e("TAG", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPager = new LoadingPagerImpl(context);
        mLoadingPager.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        mUnbinder = ButterKnife.bind(this, mLoadingPager);
//        Log.e("TAG", "onCreateView");
        return mLoadingPager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        isLazy=setLazy();
        if (!isLazy) {
            LogUtils.d("正常加载数据");
            initData();
        }
        if (getUserVisibleHint() && isLazy) {
            if(!isLoaded) {
                initData();
                LogUtils.d("懒加载一次" + this.toString());
                isLoaded = true;
            }
        }
        isInited = true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isLoaded",isLoaded);
        outState.putBoolean("isLazy",isLazy);
        outState.putBoolean("isInited",isInited);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isLazy = setLazy();
        /**
         * 此处的初始化，是当显示状态切换的时候，根据不同的状态进行初始化
         * isInited 保证fragment创建时，不初始化，因为此方法会在创建时，第一个被调用，如果此时初始化，会导致空指针
         * isLoaded 确保每个fragment只初始化一次数据
         */
        if (isVisibleToUser && isInited && !isLoaded && isLazy) {
            LogUtils.d("懒加载一次" + this.toString());

            initData();
            isLoaded = true;
        }
    }

    /**
     * 返回mvp presenter层
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 布局id
     *
     * @return
     */
    protected abstract int layoutId();

    /**
     * 初始化数据
     * 在请求数据完成后，根据状态，更改loadingPager的状态，以正确的显示页面
     */
    protected abstract void initData();


    /**
     * 更改loadingPager的状态
     *
     * @param state
     */
    public void changeLoadingPagerState(int state) {
        mLoadingPager.changeState(state);
    }


    @Override
    public void showError() {
        if(mLoadingPager.getCurrentState()==LoadingPager.STATE_ERROR){
            return;
        }
        mLoadingPager.changeState(LoadingPager.STATE_ERROR);
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * LoadingPager的实现类
     */
    class LoadingPagerImpl extends LoadingPager {

        public LoadingPagerImpl(@NonNull Context context) {
            this(context, null);
        }

        public LoadingPagerImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public LoadingPagerImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public int successLayoutId() {
            return layoutId();
        }
    }
}
