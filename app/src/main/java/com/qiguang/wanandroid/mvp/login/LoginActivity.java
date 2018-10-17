package com.qiguang.wanandroid.mvp.login;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.base.BaseActivity;
import com.qiguang.wanandroid.bean.LoginRegisterBean;
import com.qiguang.wanandroid.mvp.register.RegisterActivity;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:13
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View, android.view.View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initData() {
        setListener();
    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                remove();
            }
        });
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void success() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
        remove();
    }

    public void error(String error) {
        etPassword.setText("");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoRegister() {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(android.view.View v) {
        if (v == btnLogin) {
            login();
        } else if (v == btnRegister) {
            gotoRegister();
        }
    }

    @Override
    public void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        presenter.login(username,password);
    }
}
