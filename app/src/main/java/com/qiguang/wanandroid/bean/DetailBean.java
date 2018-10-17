package com.qiguang.wanandroid.bean;

import java.io.Serializable;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午7:37
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class DetailBean implements Serializable {
    /**
     * 标题
     */
    private String title;
    /**
     * 要加载的url
     */
    private String url;
    /**
     * 是否收藏
     */
    private boolean isCollection;
    /**
     * 描述
     */
    private String description;

    /**
     * 文章id
     */
    private int id;

    public DetailBean(String title, String url, boolean isCollection, String description, int id) {
        this.title = title;
        this.url = url;
        this.isCollection = isCollection;
        this.description = description;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
