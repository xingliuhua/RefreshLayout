package com.xingliuhua.refreshlayout;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingliuhua.lib_refreshlayout.AbsRefreshFooter;


/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public class MyRefreshLayoutFooter extends AbsRefreshFooter {
    private TextView mTvLoadMore;

    public MyRefreshLayoutFooter(Context context) {
        super(context);
        init();
    }

    private void init() {
        mTvLoadMore = new TextView(getContext());
        mTvLoadMore.setText("上拉加载更多");
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        mTvLoadMore.setGravity(Gravity.CENTER);
        addView(mTvLoadMore, layoutParams);
    }


    @Override
    public void onStartLoadMore() {
        mTvLoadMore.setText("加载中...");
        LayoutParams layoutParams = (LayoutParams) mTvLoadMore.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL + Gravity.TOP;
        mTvLoadMore.requestLayout();
    }


    public void onPull(float dy) {
        setVisibility(ViewGroup.VISIBLE);

        getLayoutParams().height = -(int) dy;
        requestLayout();
    }

    @Override
    public void onFinishLoadMore() {
        mTvLoadMore.setText("上拉加载更多");
    }


}
