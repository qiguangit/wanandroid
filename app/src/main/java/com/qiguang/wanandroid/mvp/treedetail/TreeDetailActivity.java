package com.qiguang.wanandroid.mvp.treedetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.bean.TreeBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.treedetail.fragment.TreeDetailItemFragment;
import com.qiguang.wanandroid.utils.UIUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午7:59
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_tree_detail)
    ViewPager vpTreeDetail;
    @BindView(R.id.iv_goto_top)
    ImageView ivGotoTop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TreeBean.DataBean mData;
    private ArrayList<BaseFragment> mFragments;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_tree_detail;
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> remove());
        mData = (TreeBean.DataBean) getIntent().getSerializableExtra(Constant.DATA);
        tvTitle.setText(mData.getName());
        initViewPager();
        ivGotoTop.setOnClickListener(v -> {
            TreeDetailItemFragment fragment = (TreeDetailItemFragment) mFragments.get(vpTreeDetail.getCurrentItem());
            fragment.scrollToTop();
        });
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        List<TreeBean.DataBean.ChildrenBean> children = mData.getChildren();
        for (int i = 0; i < children.size(); i++) {
            TreeDetailItemFragment itemFragment = new TreeDetailItemFragment();
            itemFragment.setData(0, children.get(i).getId());
            mFragments.add(itemFragment);
        }
        initMagicIndicator();
        vpTreeDetail.setAdapter(new TreeDetailAdapter(getSupportFragmentManager(), mFragments));
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(UIUtils.getColor(R.color.indicatorBgColor));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mData.getChildren() == null ? 0 : mData.getChildren().size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mData.getChildren().get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpTreeDetail.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(UIUtils.getColor(R.color.indicator));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, vpTreeDetail);
    }

    private class TreeDetailAdapter extends FragmentStatePagerAdapter {

        private ArrayList<BaseFragment> mFragments;

        public TreeDetailAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
