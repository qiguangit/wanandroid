package com.qiguang.wanandroid.mvp.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.adapter.BaseFragmentPagerAdapter;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseShowHideFragment;
import com.qiguang.wanandroid.event.SwitchNightEvent;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.main.home.HomeFragment;
import com.qiguang.wanandroid.mvp.main.navigation.NavigationFragment;
import com.qiguang.wanandroid.mvp.main.project.ProjectFragment;
import com.qiguang.wanandroid.mvp.main.tree.TreeFragment;
import com.qiguang.wanandroid.utils.BottomNavigationViewHelper;
import com.qiguang.wanandroid.view.LoadingPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 上午10:28
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class MainFragment extends BaseShowHideFragment {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    private BaseFragmentPagerAdapter mAdapter;

    @Override
    public boolean setLazy() {
        return false;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearFragmentManager(SwitchNightEvent event){
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (Fragment item :
                fragments) {
            transaction.remove(item);
        }
        transaction.commit();
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
//        Toast.makeText(context, "main 加载一次", Toast.LENGTH_SHORT).show();
        BottomNavigationViewHelper.disableShiftMode(navigation);
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
        navigation.setSelectedItemId(R.id.navigation_home);
        mAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(new HomeFragment());
        mAdapter.addFragment(new TreeFragment());
        mAdapter.addFragment(new NavigationFragment());
        mAdapter.addFragment(new ProjectFragment());
        vpMain.setOffscreenPageLimit(3);
        vpMain.setAdapter(mAdapter);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int id;
                switch (position) {
                    case 0:
                        id = R.id.navigation_home;
                        break;
                    case 1:
                        id = R.id.navigation_tree;
                        break;
                    case 2:
                        id = R.id.navigation_nav;
                        break;
                    case 3:
                        id = R.id.navigation_project;
                        break;
                    default:
                        id = R.id.navigation_home;
                        break;
                }
                navigation.setSelectedItemId(id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        vpMain.setCurrentItem(0);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        vpMain.setCurrentItem(0, false);
                        break;
                    case R.id.navigation_tree:
                        vpMain.setCurrentItem(1, false);
                        break;
                    case R.id.navigation_nav:
                        vpMain.setCurrentItem(2, false);
                        break;
                    case R.id.navigation_project:
                        vpMain.setCurrentItem(3, false);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
