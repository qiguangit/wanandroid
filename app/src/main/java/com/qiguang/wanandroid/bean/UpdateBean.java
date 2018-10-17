package com.qiguang.wanandroid.bean;

import java.io.Serializable;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-10-13 下午11:00
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class UpdateBean implements Serializable {

    /**
     * version : 1.0
     * url : http://localhost:8080/wanandroid/wanandroid2_0.apk
     * name : wanandroid2_0.apk
     * update_time : 2018-10-08 11:00:09
     * message : wanandroid更新至2.0啦，速来更新
     */

    private String version;
    private String url;
    private String name;
    private String update_time;
    private String message;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
