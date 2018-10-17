package com.qiguang.wanandroid.mvp;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-8-19 下午10:19
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface IView {
    /**
     * 显示错误页面
     */
    void showError();

    /**
     * 淡出提示用户toast
     * @param msg
     */
    void showErrorMsg(String msg);
}
