package com.qiguang.wanandroid.mvp.main.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qiguang.wanandroid.mvp.main.project.detail.ProjectDetailFragment;

import java.util.ArrayList;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:26
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ProjectViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<ProjectDetailFragment> fragments;

    public ProjectViewPagerAdapter(FragmentManager fm, ArrayList<ProjectDetailFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
