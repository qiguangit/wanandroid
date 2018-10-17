package com.qiguang.wanandroid.mvp;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-8-19 下午10:19
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface IPresenter {
    /**
     * 绑定
     */
    void attach();

    /**
     * 解绑
     */
    void detach();
}
