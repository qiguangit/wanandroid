package com.qiguang.wanandroid.mvp.treedetail;

import com.qiguang.wanandroid.mvp.BasePresenter;
import com.qiguang.wanandroid.mvp.main.tree.TreeContract;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午8:11
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class TreeDetailPresenter extends BasePresenter<TreeDetailContract.View> implements TreeContract.Presenter{

    public TreeDetailPresenter(TreeDetailContract.View view) {
        super(view);
    }

    @Override
    public void requestTree() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }
}
