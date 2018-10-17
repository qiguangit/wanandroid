package com.qiguang.wanandroid.mvp.common_net_address;

import com.qiguang.wanandroid.bean.CommonNetAddressBean;
import com.qiguang.wanandroid.common.Constant;
import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.CommonObserver;
import com.qiguang.wanandroid.retrofit.RetrofitClient;
import com.qiguang.wanandroid.retrofit.RetrofitService;
import com.qiguang.wanandroid.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午3:29
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class CommonNetAddressPresenter extends BasePresenter<CommonNetAddressContract.View> implements CommonNetAddressContract.Presenter {
    public CommonNetAddressPresenter(CommonNetAddressContract.View view) {
        super(view);
    }

    @Override
    public void requestCommonNetAddress() {
        addSubscribe(RetrofitClient.getInstance().create(RetrofitService.class)
                .requestCommonNetAddress()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonObserver<CommonNetAddressBean>(view){
                    @Override
                    public void onSuccess(CommonNetAddressBean commonNetAddressBean) {
                        view.setupCommonNetAddress(commonNetAddressBean);
                    }
                }));
    }
}
