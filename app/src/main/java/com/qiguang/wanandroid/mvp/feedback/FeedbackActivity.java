package com.qiguang.wanandroid.mvp.feedback;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.bumptech.glide.Glide;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.BaseBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.mvp.IView;
import com.qiguang.wanandroid.mvp.main.MainActivity;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.UIUtils;
import com.qiguang.wanandroid.utils.UriUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-8 上午9:12
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.iv_add_image)
    ImageView ivAddImage;
    @BindView(R.id.ll_images)
    LinearLayout llImages;
    private int REQUEST_CODE_CHOOSE = 0;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> remove());
    }


    @OnClick({R.id.iv_add_image, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_image:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(9)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.qb_px_120))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.btn_commit:
                commit();
                break;
            default:
                break;
        }
    }

    private void commit() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        String content = etFeedback.getText().toString();
        Map<String, String> fields = new HashMap<>(10);
        Map<String, RequestBody> maps = new HashMap<>(10);

        if (!TextUtils.isEmpty(content)) {

            String contact = etContact.getText().toString();
            builder.addFormDataPart(Constant.Feedback.CONTENT, content);
            if (TextUtils.isEmpty(contact)) {
                contact = "";
            }

            for (Uri item :
                    mSelected) {
                String realFilePath = UriUtils.getRealFilePath(this, item);
                String fileName = UriUtils.path2Name(realFilePath);
                builder.addFormDataPart("file", fileName, RequestBody.create(MediaType.parse("image/*"), new File(realFilePath)));
            }
            builder.addFormDataPart(Constant.Feedback.CONTACT, contact);
            String model = DeviceUtils.getModel();
            builder.addFormDataPart(Constant.Feedback.PHONE_TYPE, model);

            RetrofitClient.getInstance().create(RetrofitService.class)
                    .feedback(builder.build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonObserver<BaseBean>(this) {
                        @Override
                        public void onSuccess(BaseBean baseBean) {
                            Toast.makeText(FeedbackActivity.this, "反馈成功，感谢你的反馈，我们会做的更好的", Toast.LENGTH_SHORT).show();
                            remove();
                        }
                    });
        } else {
            Toast.makeText(this, "反馈内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            addImages(mSelected);
        }
    }

    private void addImages(List<Uri> uris) {
        for (Uri item : uris) {
            ImageView iv = new ImageView(this);
            int width = (int) getResources().getDimension(R.dimen.qb_px_45);
            iv.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            llImages.addView(iv, 0);
            Glide.with(this)
                    .load(item)
                    .into(iv);
        }

    }

}
