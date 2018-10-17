package com.qiguang.wanandroid.mvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.BaseBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.event.UpdateLoginStateEvent;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.mvp.about.AboutActivity;
import com.qiguang.wanandroid.mvp.collect.CollectListFragment;
import com.qiguang.wanandroid.mvp.login.LoginActivity;
import com.qiguang.wanandroid.mvp.setting.SettingFragment;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-22 下午3:53
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class MainActivity extends BaseActivity<MainContract.Presenter>
        implements MainContract.View,NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    TextView tvUsername;
    private ActionBarDrawerToggle mToggle;
    private boolean isRestore = false;
    private String currentTag;
    private Handler mHandler;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentTag = savedInstanceState.getString("tag");
            isRestore = true;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initData() {
        JPushInterface.requestPermission(this);
        EventBus.getDefault().register(this);
        navView.setNavigationItemSelectedListener(this);
        NotificationManagerCompat.from(this).areNotificationsEnabled();
        navView.setCheckedItem(R.id.nav_index);
        tvUsername = navView.getHeaderView(0).findViewById(R.id.tv_username);
        navView.getHeaderView(0).setOnClickListener(v -> {
            if (!SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        initDrawerLayout();
        if (!isRestore) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constant.Tag.TAG_MAIN);
            if (fragment == null) {
                fragment = new MainFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_main, fragment, Constant.Tag.TAG_MAIN)
                        .show(fragment)
                        .commit();
            }
            currentTag = Constant.Tag.TAG_MAIN;
        }
        updateState();
        checkUpdate();
        checkNotificationPermission();
    }

    private void checkNotificationPermission() {
        presenter.checkNotificationPermission();
    }

    private void checkUpdate() {
        presenter.checkUpdate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginState(UpdateLoginStateEvent event) {
        updateState();
    }

    private void updateState() {
        boolean isLogin = SPUtils.getInstance().getBoolean(Constant.IS_LOGIN);
        if (isLogin) {
            tvUsername.setText(SPUtils.getInstance().getString(Constant.USERNAME));
        } else {
            tvUsername.setText("点击登陆");
        }
    }

    public void switchNavigation(boolean isOpen) {
        if (isOpen) {
            drawerLayout.openDrawer(Gravity.START);
        } else {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    private void initDrawerLayout() {
        //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
        //获取抽屉的view
        //设置左边菜单滑动后的占据屏幕大小
        //设置菜单透明度
        //设置内容界面水平和垂直方向偏转量
        //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
        //设置内容界面操作无效（比如有button就会点击无效）
        //设置右边菜单滑动后的占据屏幕大小
        mToggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = drawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        mToggle.syncState();
        drawerLayout.addDrawerListener(mToggle);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        //避免内存泄露
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        drawerLayout.removeDrawerListener(mToggle);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_index:
                switchShowFragment(Constant.Tag.TAG_MAIN);
                break;
            case R.id.nav_collect:
                if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
                    switchShowFragment(Constant.Tag.TAG_COLLECT);
                } else {
                    Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_setting:
                switchShowFragment(Constant.Tag.TAG_SETTING);
                break;
            case R.id.nav_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                RetrofitClient.getInstance().create(RetrofitService.class)
                        .logout()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new CommonObserver<BaseBean>(this) {

                            @Override
                            public void onSuccess(BaseBean baseBean) {
                                Toast.makeText(MainActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                                SPUtils.getInstance().remove(Constant.IS_LOGIN);
                                SPUtils.getInstance().remove(Constant.USERNAME);
                                updateState();
                            }
                        });
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tag", currentTag);
    }

    private void switchShowFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            //当前fragment没有添加
            switch (tag) {
                case Constant.Tag.TAG_MAIN:
                    fragment = new MainFragment();
                    break;
                case Constant.Tag.TAG_COLLECT:
                    fragment = new CollectListFragment();
                    break;
                case Constant.Tag.TAG_SETTING:
                    fragment = new SettingFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main, fragment, tag)
                    .hide(getSupportFragmentManager().findFragmentByTag(currentTag))
                    .show(fragment)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(getSupportFragmentManager().findFragmentByTag(currentTag))
                    .show(fragment)
                    .commitAllowingStateLoss();
        }
        currentTag = tag;
    }

}
