package com.qiguang.wanandroid.bean;

import java.io.Serializable;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-20 下午10:48
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class BaseBean implements Serializable {
    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
