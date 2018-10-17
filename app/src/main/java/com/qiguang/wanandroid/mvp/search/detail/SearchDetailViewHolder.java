package com.qiguang.wanandroid.mvp.search.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qiguang.wanandroid.R;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-16 下午8:02
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class SearchDetailViewHolder extends BaseViewHolder {
     ImageView ivIcon;
     TextView tvAuthor;
     TextView superChapter;
     TextView chapter;
     TextView tvTitle;
     ImageView ivCollection;
     ImageView ivTime;
     TextView tvTime;


    public SearchDetailViewHolder(View view) {
        super(view);
        findViews(view);
    }



    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-09-16 20:12:40 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View itemView) {
        ivIcon = (ImageView)itemView.findViewById( R.id.iv_icon );
        tvAuthor = (TextView)itemView.findViewById( R.id.tv_author );
        superChapter = (TextView)itemView.findViewById( R.id.super_chapter );
        chapter = (TextView)itemView.findViewById( R.id.chapter );
        tvTitle = (TextView)itemView.findViewById( R.id.tv_title );
        ivCollection = (ImageView)itemView.findViewById( R.id.iv_collection );
        ivTime = (ImageView)itemView.findViewById( R.id.iv_time );
        tvTime = (TextView)itemView.findViewById( R.id.tv_time );
    }


}
