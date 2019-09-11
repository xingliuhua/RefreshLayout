package com.xingliuhua.refreshlayout;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.xingliuhua.lib_refreshlayout.AbsRefreshHeder;

/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public class MyRefreshLayoutHeader extends AbsRefreshHeder {

    private float mIvHeight = 150f;


    public MyRefreshLayoutHeader(Context context) {
        super(context);
        init();
    }

    private LottieAnimationView mIv;
    private View contentView;

    private void init() {
        contentView = View.inflate(getContext(), R.layout.header, null);
        mIv = contentView.findViewById(R.id.animation_view);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) mIvHeight);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        addView(contentView, layoutParams);
    }

    @Override
    public void onStartRefreshing() {
        mIv.playAnimation();
    }

    @Override
    public void onPull(float dy) {
        Log.e("xx", "onpull");
        setVisibility(View.VISIBLE);
        if (dy < mIvHeight) {
            mIv.getLayoutParams().height = (int) dy;
            mIv.requestLayout();
            getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            requestLayout();
            mIv.setProgress(dy / mIvHeight);
        } else {
            getLayoutParams().height = (int) dy;
            requestLayout();
            contentView.setTranslationY(dy - mIvHeight);
        }
    }

    @Override
    public void onFinishRefreshing() {
        mIv.cancelAnimation();
    }

}
