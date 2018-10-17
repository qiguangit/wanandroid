package com.qiguang.wanandroid.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-16 上午8:21
 * @Description: 通用对话框传递数据的bean
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class DialogBean implements Serializable{
    /**
     * 标题
     */
    private String title;
    /**
     * 消息
     */
    private String message;
    /**
     * icon
     */
    private Drawable icon;

    /**
     * 确认
     */
    private String okText;

    /**
     * 返回
     */
    private String cancelText;

    public DialogBean(String title, String message, Drawable icon, String okText, String cancelText) {
        this.title = title;
        this.message = message;
        this.icon = icon;
        this.okText = okText;
        this.cancelText = cancelText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getOkText() {
        return okText;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }
}
