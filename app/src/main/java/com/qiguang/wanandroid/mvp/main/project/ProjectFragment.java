package com.qiguang.wanandroid.mvp.main.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseFragment;
import com.qiguang.wanandroid.base.BaseViewPagerFragment;
import com.qiguang.wanandroid.bean.ProjectTitleBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.common_net_address.CommonNetAddressActivity;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.mvp.main.project.detail.ProjectDetailFragment;
import com.qiguang.wanandroid.mvp.search.SearchActivity;
import com.qiguang.wanandroid.utils.UIUtils;
import com.qiguang.wanandroid.view.LoadingPager;

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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:37
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class ProjectFragment extends BaseViewPagerFragment<ProjectContract.Presenter> implements ProjectContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_project)
    ViewPager vpProject;
    @BindView(R.id.iv_goto_top)
    ImageView ivGotoTop;
    private ArrayList<ProjectDetailFragment> mFragments;

    @Override
    public boolean setLazy() {
        return true;
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_project;

    }

    @Override
    protected void initData() {
        setupToolbar();
        ivGotoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragments.get(vpProject.getCurrentItem()).gotoTop();
            }
        });
        presenter.requestIndicator();

    }

    private void setupToolbar() {
        tvTitle.setText("项目");
        toolbar.setNavigationIcon(R.drawable.nav);
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).switchNavigation(true);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.website){
                    Intent intent=new Intent(context, CommonNetAddressActivity.class);
                    context.startActivity(intent);
                }else if(item.getItemId()==R.id.search){
                    Intent intent=new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                }
                return false;
            }
        });
    }

    private void initMagicIndicator(final List<ProjectTitleBean.DataBean> dataBean) {
        magicIndicator.setBackgroundColor(UIUtils.getColor(R.color.indicatorBgColor));
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataBean == null ? 0 : dataBean.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(dataBean.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpProject.setCurrentItem(index);
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
        ViewPagerHelper.bind(magicIndicator, vpProject);
    }

    @Override
    public void setupIndicator(ProjectTitleBean bean) {
        List<ProjectTitleBean.DataBean> data = bean.getData();
        initViewPager(bean);
        initMagicIndicator(data);
        changeLoadingPagerState(LoadingPager.STATE_SUCCESS);
    }

    private void initViewPager(ProjectTitleBean bean) {
        mFragments = new ArrayList<>();
        for(ProjectTitleBean.DataBean item :bean.getData()){
            ProjectDetailFragment fragment=new ProjectDetailFragment();
            fragment.setData(0,item.getId());
            mFragments.add(fragment);
        }
        vpProject.setAdapter(new ProjectViewPagerAdapter(getChildFragmentManager(), mFragments));
    }

}
