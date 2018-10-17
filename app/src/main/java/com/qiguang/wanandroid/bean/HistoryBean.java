package com.qiguang.wanandroid.bean;

import java.io.Serializable;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午1:01
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class HistoryBean implements Serializable {
    private int id;
    private long timestamp;
    private String key;

    public HistoryBean(int id, long timestamp, String key) {
        this.id = id;
        this.timestamp = timestamp;
        this.key = key;
    }

    public HistoryBean(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
