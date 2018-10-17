package com.qiguang.wanandroid.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.bean.DialogBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-16 上午8:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CommonDialog extends DialogFragment {

    public static final String DATA = "data";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.seekbar)
    ProgressBar seekBar;
    Unbinder unbinder;
    private DialogOnClickListener listener;

    public static CommonDialog getInstance(DialogBean bean) {
        CommonDialog dialog = new CommonDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA, bean);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.dialog_common, null);
        unbinder = ButterKnife.bind(this, dialog);
        Bundle arguments = getArguments();
        if (arguments != null) {
            DialogBean bean = (DialogBean) arguments.getSerializable(DATA);
            if (bean != null) {
                tvTitle.setText(bean.getTitle());
                tvContent.setText(bean.getMessage());
                btnOk.setText(bean.getOkText());
                btnCancel.setText(bean.getCancelText());
            }
        }
        return dialog;
    }


    public void setOnClickListener(DialogOnClickListener listener) {
        this.listener = listener;
    }

    public void setTotalSize(int size) {
        seekBar.setMax(size);
    }

    public void setCurrentSize(int size) {
        seekBar.setProgress(size);
    }

    public void setSeekBarVisible(int visible) {
        seekBar.setVisibility(visible);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                if (listener != null) {
                    listener.cancelClick(view);
                } else {
                    dismiss();
                }
                break;
            case R.id.btn_ok:
                if (listener != null) {
                    listener.okClick(view);
                } else {
                    dismiss();
                }
                break;

            default:
                break;
        }
    }


    public interface DialogOnClickListener {
        /**
         * 确认键被点击
         *
         * @param v
         */
        void okClick(View v);

        /**
         * 取消键被点击
         *
         * @param v
         */
        void cancelClick(View v);
    }
}
