package com.qiguang.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qiguang.wanandroid.base.BaseFragment;

import java.util.ArrayList;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:28
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> mFragments=new ArrayList<>(4);

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(BaseFragment fragment){
        mFragments.add(fragment);
    }
}
