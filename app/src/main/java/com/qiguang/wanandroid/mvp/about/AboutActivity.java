package com.qiguang.wanandroid.mvp.about;

import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.utils.StatusBarUtil;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-5 下午5:47
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.mountain)
    MountainSceneView mountain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.flyRefresh)
    FlyRefreshHeader flyRefresh;
    @BindView(R.id.tv_awesome)
    TextView tvAwesome;
    @BindView(R.id.tv_about_content)
    TextView tvAboutContent;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.about_us_content)
    NestedScrollView aboutUsContent;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.flyView)
    FlyView flyView;

    private View.OnClickListener mThemeListener;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {

        try {
            String versionStr = getString(R.string.awesome_wan_android)
                    + " V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tvAwesome.setText(versionStr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvAboutContent.setText(Html.fromHtml(getResources().getString(R.string.about_content)));
        refreshLayout.autoRefresh(300,1500,1f);
        tvAboutContent.setMovementMethod(LinkMovementMethod.getInstance());
        toolbar.setNavigationOnClickListener(v -> {
//            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            recreate();
            remove();
        });
        setSmartRefreshLayout();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTheme();
                refreshLayout.autoRefresh();
            }
        });
        /*
         * 监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 ActionButton 设置关闭隐藏动画
         */
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean misAppbarExpand = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                if (fraction < 0.1 && misAppbarExpand) {
                    misAppbarExpand = false;
                    fab.animate().scaleX(0).scaleY(0);
                    flyView.animate().scaleX(0).scaleY(0);
                    ValueAnimator animator = ValueAnimator.ofInt(aboutUsContent.getPaddingTop(), 0);
                    animator.setDuration(300);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            aboutUsContent.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.start();
                }
                if (fraction > 0.8 && !misAppbarExpand) {
                    misAppbarExpand = true;
                    fab.animate().scaleX(1).scaleY(1);
                    flyView.animate().scaleX(1).scaleY(1);
                    ValueAnimator animator = ValueAnimator.ofInt(aboutUsContent.getPaddingTop(), DensityUtil.dp2px(25));
                    animator.setDuration(300);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            aboutUsContent.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.start();
                }
            }
        });


        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);

    }

    private void setSmartRefreshLayout() {
        //绑定场景和纸飞机
        flyRefresh.setUp(mountain, flyView);
        refreshLayout.setReboundInterpolator(new ElasticOutInterpolator());
        refreshLayout.setReboundDuration(800);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            updateTheme();
            refreshLayout.finishRefresh(1000);
        });

        //设置让Toolbar和AppBarLayout的滚动同步
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {

            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                if (appbar == null || toolbar == null) {
                    return;
                }
                appbar.setTranslationY(offset);
                toolbar.setTranslationY(-offset);
            }


            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
                if (appbar == null || toolbar == null) {
                    return;
                }
                appbar.setTranslationY(offset);
                toolbar.setTranslationY(-offset);
            }
        });
    }

    private void updateTheme() {
        if (mThemeListener == null) {
            mThemeListener = new View.OnClickListener() {
                int index = 0;
                int[] ids = new int[]{
                        R.color.colorPrimary,
                        android.R.color.holo_green_light,
                        android.R.color.holo_red_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright,
                };

                @Override
                public void onClick(View v) {
                    int color = ContextCompat.getColor(getApplication(), ids[index % ids.length]);
                    refreshLayout.setPrimaryColors(color);
                    fab.setBackgroundColor(color);
                    fab.setBackgroundTintList(ColorStateList.valueOf(color));
                    toolbarLayout.setContentScrimColor(color);
                    index++;
                }
            };
        }
        mThemeListener.onClick(null);
    }


    public class ElasticOutInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float t) {
            if (t == 0) {
                return 0;
            }
            if (t >= 1) {
                return 1;
            }
            float p = .3f;
            float s = p / 4;
            return ((float) Math.pow(2, -10 * t) * (float) Math.sin((t - s) * (2 * (float) Math.PI) / p) + 1);
        }
    }

}
