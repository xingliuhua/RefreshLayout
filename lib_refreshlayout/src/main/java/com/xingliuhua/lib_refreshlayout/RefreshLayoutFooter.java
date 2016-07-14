package com.xingliuhua.lib_refreshlayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public class RefreshLayoutFooter extends FrameLayout implements IRefreshFooter {
    private String mFooterPullText;
    private String mFooterLoadmoreingText;
    private TextView mTvLoadMore;
    public RefreshLayoutFooter(Context context, String pullText, String loadmoreingText) {
        super(context);
        this.mFooterPullText = pullText;
        this.mFooterLoadmoreingText = loadmoreingText;
        init();
    }
    public void setNeedLoadMoreMessage(String message){
        mTvLoadMore.setText(message);
    }

    private void init() {
        mTvLoadMore = new TextView(getContext());
        if (!TextUtils.isEmpty(mFooterPullText)) {
            mTvLoadMore.setText(mFooterPullText);
        } else {
            mTvLoadMore.setText("Pull to refresh…");
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        mTvLoadMore.setGravity(Gravity.CENTER);
        addView(mTvLoadMore, layoutParams);
    }


    @Override
    public void onStartLoadMore() {
        if (!TextUtils.isEmpty(mFooterLoadmoreingText)) {
            mTvLoadMore.setText(mFooterLoadmoreingText);
        } else {
            mTvLoadMore.setText("loading");
        }
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
        if (!TextUtils.isEmpty(mFooterPullText)) {
            mTvLoadMore.setText(mFooterPullText);
        } else {
            mTvLoadMore.setText("Pull to refresh…");
        }
    }


}
