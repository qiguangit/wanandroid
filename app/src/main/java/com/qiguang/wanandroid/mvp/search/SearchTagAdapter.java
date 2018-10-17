package com.qiguang.wanandroid.mvp.search;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qiguang.wanandroid.R;
import com.qiguang.wanandroid.bean.HotSearchBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;
import java.util.Random;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-13 下午9:36
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SearchTagAdapter extends TagAdapter<HotSearchBean.DataBean> {
    public SearchTagAdapter(List<HotSearchBean.DataBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, HotSearchBean.DataBean dataBean) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_address, null);
        int r=new Random().nextInt(255);
        int g=new Random().nextInt(255);
        int b=new Random().nextInt(255);
        textView.setBackgroundColor(Color.rgb(r,g,b));
        textView.setText(dataBean.getName());
        return textView;
    }
}
