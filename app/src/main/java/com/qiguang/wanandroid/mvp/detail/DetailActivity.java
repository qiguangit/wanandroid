package com.qiguang.wanandroid.mvp.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.DetailBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.login.LoginActivity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:00
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class DetailActivity extends BaseActivity<DetailContract.Presenter> implements DetailContract.View {
    @BindView(R.id.toolbar_webview)
    Toolbar toolbarWebview;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private DetailBean mBean;
    private MenuItem mCollect;
    private boolean isCollect;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initData() {
        mBean = (DetailBean) getIntent().getSerializableExtra(Constant.DATA);
        isCollect = mBean.isCollection();
        tvTitle.setText(mBean.getTitle());
        toolbarWebview.setTitleTextAppearance(this, R.style.Toolbar_TitleText);
        toolbarWebview.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarWebview);
        toolbarWebview.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    remove();
                }
            }
        });
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.loadUrl(mBean.getUrl());
        progressBar.setMax(100);
        webview.setWebChromeClient(new MyWebChromeClient(progressBar));
        webview.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view, menu);
        mCollect = menu.findItem(R.id.menu_collection);
        if (isCollect) {
            mCollect.setIcon(R.drawable.heart);
        } else {
            mCollect.setIcon(R.drawable.no_heart);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_collection:
                if (!SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
                    Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (isCollect) {
                        presenter.uncollect(mBean.getId());
                    } else {
                        presenter.collect(mBean.getId());
                    }
                }
                break;
            case R.id.menu_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_other_open:
                Toast.makeText(this, "用系统浏览", Toast.LENGTH_SHORT).show();
                otherOpen();
                break;
            default:
                break;
        }
        //        return true;
        return super.onOptionsItemSelected(item);
    }
    //

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu      Menu
     * @return menu if opened
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public void collect() {
        isCollect = true;
        mCollect.setIcon(R.drawable.heart);
    }

    @Override
    public void uncollect() {
        isCollect = false;
        mCollect.setIcon(R.drawable.no_heart);
    }

    @Override
    public void share() {

    }

    @Override
    public void otherOpen() {
        Uri uri = Uri.parse(mBean.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.destroy();
        }

    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    static class MyWebChromeClient extends WebChromeClient {
        WeakReference<ProgressBar> weakReference;

        public MyWebChromeClient(ProgressBar progressBar) {
            weakReference = new WeakReference<ProgressBar>(progressBar);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            ProgressBar progressBar = weakReference.get();
            if (progressBar != null) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }
    }

}
