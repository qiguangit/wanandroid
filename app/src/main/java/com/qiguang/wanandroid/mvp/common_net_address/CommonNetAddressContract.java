package com.qiguang.wanandroid.mvp.common_net_address;

import com.qiguang.wanandroid.bean.CommonNetAddressBean;
import com.qiguang.wanandroid.mvp.IPresenter;
import com.qiguang.wanandroid.mvp.IView;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午3:29
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public interface CommonNetAddressContract {
    interface Presenter extends IPresenter{
        /**
         * 请求常用网站
         */
        void requestCommonNetAddress();
    }

    interface View extends IView{
        /**
         * 设置常用网站数据
         * @param bean
         */
        void setupCommonNetAddress(CommonNetAddressBean bean);
    }
}
