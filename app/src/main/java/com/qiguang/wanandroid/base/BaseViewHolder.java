package com.qiguang.wanandroid.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiguang.wanandroid.adapter.OnItemClickListener;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 2018 下午4:17
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private BaseRecyclerViewAdapter adapter;

    public void setAdapter(BaseRecyclerViewAdapter adapter){
        this.adapter=adapter;
    }

    public BaseViewHolder(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemClickListener listener = adapter.getItemClickListener();
                if(listener!=null){
                    listener.onItemClick(itemView,getLayoutPosition());
                }
            }
        });
    }
}
