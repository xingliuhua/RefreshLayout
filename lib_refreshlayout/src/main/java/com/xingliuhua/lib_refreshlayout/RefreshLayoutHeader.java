package com.xingliuhua.lib_refreshlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public class RefreshLayoutHeader extends FrameLayout implements IRefreshHeder {

    private AnimationDrawable mAnimationDrawable;
    private float mIvHeight = 100f;


    public RefreshLayoutHeader(Context context, int imageResId) {
        super(context);
        init(imageResId);
    }

    private ImageView mIv;

    private void init(int imageResId) {
        mIv = new ImageView(getContext());
        if (imageResId == -1) {
            mIv.setImageResource(R.drawable.refreshing);
        }else{
            mIv.setImageResource(imageResId);
        }
        mIv.setPadding(0,10,0,10);
        mIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) mIvHeight);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        addView(mIv, layoutParams);
        mAnimationDrawable = (AnimationDrawable) mIv.getDrawable();
        mAnimationDrawable.stop();
    }

    @Override
    public void onStartRefreshing() {
        mAnimationDrawable.start();
    }

    public void onPull(float dy) {
        setVisibility(View.VISIBLE);
        if (dy < mIvHeight) {
            mIv.getLayoutParams().height = (int) dy;
            mIv.requestLayout();
            getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            requestLayout();
        } else {
            getLayoutParams().height = (int) dy;
            requestLayout();
            mIv.setTranslationY(dy - mIvHeight);
        }
    }

    @Override
    public void onFinishRefreshing() {
        mAnimationDrawable.stop();
    }

}
