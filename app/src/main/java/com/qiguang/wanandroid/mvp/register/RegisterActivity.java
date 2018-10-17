package com.qiguang.wanandroid.mvp.register;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午10:52
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private RegisterPresenter mPresenter;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        mPresenter = new RegisterPresenter(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功，可以登陆了", Toast.LENGTH_SHORT).show();
        remove();
    }

    @Override
    public void registerError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repassword = etRepassword.getText().toString().trim();
        mPresenter.register(username,password,repassword);
    }

}
