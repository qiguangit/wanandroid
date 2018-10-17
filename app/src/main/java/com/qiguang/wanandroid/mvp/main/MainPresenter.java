package com.qiguang.wanandroid.mvp.main;

import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.qiguang.wanandroid.App;
import com.qiguang.wanandroid.bean.DialogBean;
import com.qiguang.wanandroid.bean.UpdateBean;
import com.qiguang.wanandroid.common.AppManager;
import com.qiguang.wanandroid.dialog.CommonDialog;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.NotificationPageHelper;
import com.qiguang.wanandroid.utils.RxUtils;
import com.qiguang.wanandroid.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-16 上午10:18
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void checkUpdate() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .checkUpdate()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new DisposableObserver<UpdateBean>() {
                    @Override
                    public void onNext(UpdateBean updateBean) {
                        if (updateBean != null) {
                            if (!updateBean.getVersion().equals(AppUtils.getAppVersionName())) {
                                showUpdateDialog(updateBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void showUpdateDialog(UpdateBean updateBean) {
        CommonDialog dialog = CommonDialog.getInstance(new DialogBean("更新",
                updateBean.getMessage(),
                null,
                "愉快的更新",
                "残忍的拒绝"
        ));
        dialog.setOnClickListener(new CommonDialog.DialogOnClickListener() {
            @Override
            public void okClick(View v) {

                String path = PathUtils.getExternalDownloadsPath() + "/" + updateBean.getName();
                dialog.setSeekBarVisible(View.VISIBLE);
                addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                        .download(updateBean.getUrl())
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                download(responseBody, dialog, path);
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.e(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
            }

            @Override
            public void cancelClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show(((MainActivity) view).getSupportFragmentManager(), "update");
    }

    private void download(ResponseBody responseBody, CommonDialog dialog, String path) {
        float total = responseBody.contentLength();
        dialog.setTotalSize(100);
        dialog.setCurrentSize(0);


        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        int n = -1;
        byte[] bs = new byte[1024 * 1024];
        float sum = 0;
        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            fos = new FileOutputStream(file);
            inputStream = responseBody.byteStream();
            while ((n = inputStream.read(bs)) != -1) {
                sum += n;
                dialog.setCurrentSize((int) (100 * (sum / total)));
                fos.write(bs, 0, n);
            }
            fos.flush();
            AppUtils.installApp(path);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        }
        dialog.dismiss();
    }

    @Override
    public void checkNotificationPermission(){
        NotificationManagerCompat manager = NotificationManagerCompat.from(App.getContext());
        boolean isOpened = manager.areNotificationsEnabled();
        if (!isOpened) {
            CommonDialog dialog = CommonDialog.getInstance(new DialogBean("提示",
                    "wanandroid部分功能需要有通知的权限，是否现在进行授权？",
                    null,
                    "现在去授权",
                    "暂不授权"));
            dialog.setOnClickListener(new CommonDialog.DialogOnClickListener() {
                @Override
                public void okClick(View v) {
                    NotificationPageHelper.open((MainActivity) view);
                    dialog.dismiss();
                }

                @Override
                public void cancelClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.show(((MainActivity) view).getSupportFragmentManager(), "notification");
        }
    }
}
