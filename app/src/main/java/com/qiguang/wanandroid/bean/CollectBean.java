package com.qiguang.wanandroid.bean;

import java.io.Serializable;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午2:14
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CollectBean extends BaseBean implements Serializable {

    /**
     * data : null
     * errorCode : 0
     * errorMsg :
     */

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
