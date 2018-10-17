package com.qiguang.wanandroid.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.qiguang.wanandroid.App;
import com.qiguang.wanandroid.bean.UpdateBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-14 下午2:58
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class UpdateDialog extends DialogFragment {

    public static UpdateDialog getInstance(UpdateBean updateBean) {
        UpdateDialog dialog = new UpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA, updateBean);
        dialog.setArguments(bundle);
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            UpdateBean updateBean = (UpdateBean) bundle.getSerializable(Constant.DATA);
            if (updateBean != null) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("更新")
                        .setMessage(updateBean.getMessage() == null ? "" : updateBean.getMessage())
                        .setPositiveButton("愉快的更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                update(updateBean);
                            }
                        })
                        .setNegativeButton("残忍的拒绝", null)
                        .create();
                return dialog;
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }

    private void update(UpdateBean updateBean) {
        String path = PathUtils.getExternalDownloadsPath() + "/" + updateBean.getName();
//        RetrofitClient.getInstance().create(RetrofitService.class)
//                .download(updateBean.getUrl())
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        ResponseBody body = response.body();
//                        boolean success = FileIOUtils.writeFileFromIS(path, body.byteStream());
//                        if (success) {
//                            AppUtils.installApp(path);
//                        } else {
//                            Toast.makeText(getActivity(), "下载失败了，请检查网络", Toast.LENGTH_SHORT).show();
//                        }
//                        dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("TAG", t.getMessage());
//                        Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
//                        dismiss();
//                    }
//                });
    }
}
