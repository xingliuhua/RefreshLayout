package com.xingliuhua.lib_refreshlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.FrameLayout;


/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public class RefreshLayout extends FrameLayout {
    private View mChildView;
    private AbsRefreshHeder mRefreshHeder;
    private AbsRefreshFooter mRefreshFooter;
    private boolean isRefreshing;
    private float mTouchY;
    private float mCurrentY;
    private float mDamp;
    private final int RELEASE_MAX_HEIGHT = 150;
    private int HEADER_HEIGHT = 100;
    private int FOOTER_HEIGHT = 100;
    private boolean needLoadMore = true;
    private boolean isLoadMoreing;
    private final int ANIM_DURATION = 300;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        mDamp = (RELEASE_MAX_HEIGHT * RELEASE_MAX_HEIGHT) / 600f;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RefreshLayout);
        HEADER_HEIGHT = (int) typedArray.getDimension(R.styleable.RefreshLayout_headerHeight, 100);
        Log.e("xx", "height:" + HEADER_HEIGHT);
        FOOTER_HEIGHT = (int) typedArray.getDimension(R.styleable.RefreshLayout_footerHeight, 100);
        typedArray.recycle();


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("xx", "onAttachedToWindow");
        Context context = getContext();
        mChildView = getChildAt(getChildCount() - 1);

        if (mChildView == null) {
            return;
        }
        if (mRefreshHeder == null) {
            mRefreshHeder = new SimpleRefreshLayoutHeader(context);
            LayoutParams headerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerLayoutParams.gravity = Gravity.TOP;
            mRefreshHeder.setVisibility(View.GONE);

            addView(mRefreshHeder, 0, headerLayoutParams);
        }
        if (mRefreshFooter == null) {
            mRefreshFooter = new SimpleRefreshLayoutFooter(context);
            LayoutParams footerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerLayoutParams.gravity = Gravity.BOTTOM;
            mRefreshFooter.setVisibility(View.GONE);
            addView(mRefreshFooter, 0, footerLayoutParams);
        }
    }

    public AbsRefreshHeder getRefreshHeder() {
        return mRefreshHeder;
    }

    public void setRefreshHeder(AbsRefreshHeder refreshLayoutHeader) {
        removeView(mRefreshHeder);
        mRefreshFooter = null;
        mRefreshHeder = refreshLayoutHeader;
        LayoutParams headerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerLayoutParams.gravity = Gravity.TOP;
        mRefreshHeder.setVisibility(View.GONE);
        addView(mRefreshHeder, 0, headerLayoutParams);
    }

    public AbsRefreshFooter getRefreshFooter() {
        return mRefreshFooter;
    }

    public void setRefreshFooter(AbsRefreshFooter refreshFooter) {
        removeView(mRefreshFooter);
        mRefreshFooter = refreshFooter;
        LayoutParams footerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footerLayoutParams.gravity = Gravity.BOTTOM;
        mRefreshFooter.setVisibility(View.GONE);
        addView(mRefreshFooter, 0, footerLayoutParams);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isRefreshing || isLoadMoreing) return true;
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mTouchY;
                if (dy > 0 && !canChildScrollUp()) {
                    intercept = true;
                } else if (dy < 0 && !canChildScrollDown() && needLoadMore) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                intercept = false;
                break;
        }
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRefreshing || isLoadMoreing) {
            return super.onTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = e.getY();
                float distance = mCurrentY - mTouchY;
                // 阻尼效果
                double dy = Math.sqrt(mDamp * Math.abs(distance));
                Log.e("x", "distance:" + distance);
                if (mChildView != null) {
                    if (distance > 0 && !canChildScrollUp()) {
                        Log.e("x", "call onpull:" + dy);
                        mRefreshHeder.onPull((float) dy);
                        ViewCompat.setTranslationY(mChildView, (float) dy);
                        Log.e("x", "yidiong child:" + (float) dy);
                    } else if (distance < 0 && !canChildScrollUp()) {
                        Log.e("x", "call onpull:");
                        mRefreshHeder.onPull(0);
                        ViewCompat.setTranslationY(mChildView, 0);
                    } else if (distance < 0 && !canChildScrollDown() && needLoadMore) {
                        mRefreshFooter.onPull(-(float) dy);
                        ViewCompat.setTranslationY(mChildView, -(float) dy);
                    } else if (distance > 0 && !canChildScrollDown() && needLoadMore) {
                        mRefreshFooter.onPull(0);
                        ViewCompat.setTranslationY(mChildView, 0);
                    }
                }

                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    //下拉
                    if (ViewCompat.getY(mChildView) > 0) {
                        if (ViewCompat.getY(mChildView) >= HEADER_HEIGHT) {
                            createAnimatorTranslationY(mChildView, HEADER_HEIGHT, mRefreshHeder);
                            isRefreshing = true;
                            if (mOnRefreshListener != null) {
                                mOnRefreshListener.onRefresh();
                                mRefreshHeder.onStartRefreshing();
                            }
                        } else {
                            createAnimatorTranslationY(mChildView, 0, mRefreshHeder);
                        }
                    } else {
                        //上拉
                        if (Math.abs(ViewCompat.getY(mChildView)) >= FOOTER_HEIGHT) {
                            createAnimatorTranslationY(mChildView, -FOOTER_HEIGHT, mRefreshFooter);
                            isLoadMoreing = true;
                            if (mOnRefreshListener != null) {
                                mOnRefreshListener.onLoadmore();
                                mRefreshFooter.onStartLoadMore();
                            }
                        } else {
                            createAnimatorTranslationY(mChildView, 0, mRefreshFooter);
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(e);
    }

    public void createAnimatorTranslationY(final View v, final float h, final IRefreshHeaderOrFooter headerOrFooter) {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                float dy = interpolatedTime * (h - v.getTranslationY()) + v.getTranslationY();
                v.setTranslationY(dy);
                headerOrFooter.onPull(dy);
            }
        };
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(ANIM_DURATION);
        v.startAnimation(animation);

    }

    public void setRefreshing(boolean refreshing) {
        if (isRefreshing == refreshing) {
            return;
        }
        if (refreshing) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    startRefreshing();
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    finishRefreshing();
                }
            });
        }
    }

    private void finishRefreshing() {
        if (mChildView != null) {

            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    float dy = (1 - interpolatedTime) * HEADER_HEIGHT;
                    mChildView.setTranslationY(dy);
                    mRefreshHeder.onPull(dy);
                }
            };
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRefreshHeder.onFinishRefreshing();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(ANIM_DURATION);
            mChildView.startAnimation(animation);

        }
        isRefreshing = false;
    }

    private void finishLoadMoreing() {
        if (mChildView != null) {
            mRefreshFooter.onFinishLoadMore();
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    float dy = (1 - interpolatedTime) * FOOTER_HEIGHT;
                    mChildView.setTranslationY(-dy);
                    mRefreshFooter.onPull(-dy);
                }
            };
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRefreshFooter.onFinishLoadMore();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(ANIM_DURATION);
            mChildView.startAnimation(animation);

        }
        isLoadMoreing = false;
    }

    private void startRefreshing() {
        isRefreshing = true;
        if (mChildView != null) {
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    float dy = interpolatedTime * HEADER_HEIGHT;
                    mChildView.setTranslationY(dy);
                    mRefreshHeder.onPull(dy);
                }
            };
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(ANIM_DURATION);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRefreshHeder.onStartRefreshing();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mChildView.startAnimation(animation);

        }
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }

    }

    private void startLoadMoreing() {
        isLoadMoreing = true;
        if (mChildView != null) {
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    float dy = interpolatedTime * FOOTER_HEIGHT;
                    mChildView.setTranslationY(-dy);
                    mRefreshFooter.onPull(dy);
                }
            };
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(ANIM_DURATION);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRefreshFooter.onStartLoadMore();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mChildView.startAnimation(animation);

        }
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }

    }


    public void setLoadMoreing(boolean loadMoreing) {
        if (isLoadMoreing == loadMoreing) {
            return;
        }
        if (loadMoreing) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    startLoadMoreing();
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    finishLoadMoreing();
                }
            });
        }
    }

    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    public boolean canChildScrollDown() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                if (absListView.getChildCount() > 0) {
                    int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                    return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
                } else {
                    return false;
                }

            } else {
                return ViewCompat.canScrollVertically(mChildView, 1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, 1);
        }
    }

    private OnRefreshListener mOnRefreshListener;

    public boolean isNeedLoadMore() {
        return needLoadMore;
    }

    public void setNeedLoadMore(boolean needLoadMore) {
        this.needLoadMore = needLoadMore;
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadmore();
    }

}
