package com.qiguang.wanandroid.mvp.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.main.MainActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:49
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    private SplashHandler mHandler;

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        mHandler = new SplashHandler(new WeakReference<Activity>(this), new WeakReference<TextView>(tvTime));
        mHandler.sendEmptyMessage(0);
        SPUtils utils = SPUtils.getInstance();
        if (!utils.getBoolean(Constant.IS_FIRST_INTO)) {
            utils.put(Constant.IS_FIRST_INTO, true);
            //默认自动缓存
            utils.put(Constant.Setting.IS_AUTO_CACHE, true);
            //默认接收推送
            utils.put(Constant.Setting.IS_NO_PUSH,false);
        }
    }

    static class SplashHandler extends Handler {
        private WeakReference<Activity> activity;
        private int index = 5;
        public WeakReference<TextView> mTextViewWeakReference;

        public SplashHandler(WeakReference<Activity> activityWeakReference, WeakReference<TextView> textViewWeakReference) {
            this.mTextViewWeakReference = textViewWeakReference;
            this.activity = activityWeakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TextView textView = mTextViewWeakReference.get();
            if (textView != null) {
                textView.setText(String.valueOf(index));
                if (index >= 0) {
                    sendEmptyMessageDelayed(0, 1000);
                } else {
                    startMainActivity();
                }
                index--;
            }
        }

        private void startMainActivity() {
            if (activity.get() != null) {
                Intent intent = new Intent(activity.get(), MainActivity.class);
                activity.get().startActivity(intent);
                ((SplashActivity) activity.get()).remove();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
