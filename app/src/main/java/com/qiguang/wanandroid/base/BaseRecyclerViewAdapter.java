package com.qiguang.wanandroid.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiguang.wanandroid.adapter.OnItemClickListener;

import java.util.ArrayList;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-10 下午4:27
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public abstract class BaseRecyclerViewAdapter<H extends BaseViewHolder, D extends BaseViewHolder> extends RecyclerView.Adapter<H> {

    private int layoutId;
    private ArrayList<D> datas;
    private OnItemClickListener mItemClickListener;
    private ArrayList<View> headers = new ArrayList<>();
    private ArrayList<View> footers = new ArrayList<>();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BODY = 1;
    private static final int TYPE_FOOTER = 2;

    public BaseRecyclerViewAdapter(int layoutId, ArrayList<D> data) {
        this.layoutId = layoutId;
        this.datas = data;
    }

    public void addHeader(View header) {
        headers.add(header);
    }

    public void removeHeader(int position) {
        headers.remove(position);
    }

    public void addFooter(View footer) {
        footers.add(footer);
    }

    public void removeFooter(int position) {
        footers.remove(position);
    }


    public final OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < headers.size()) {
            return TYPE_HEADER;
        } else if (position >= headers.size() + datas.size()) {
            return TYPE_FOOTER;
        }else if (position >= headers.size() && position < datas.size() - 1) {
            return TYPE_BODY;
        }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
        rootView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        BaseViewHolder holder = new BaseViewHolder(rootView);
        holder.setAdapter(this);
        return (H) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        convert(holder, datas.get(position));
    }

    /**
     * 由外部处理数据
     *
     * @param holder
     * @param data
     */
    protected abstract void convert(H holder, D data);

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
