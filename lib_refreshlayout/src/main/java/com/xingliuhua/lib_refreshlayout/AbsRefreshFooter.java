package com.xingliuhua.lib_refreshlayout;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public abstract class AbsRefreshFooter extends FrameLayout implements IRefreshHeaderOrFooter {
    public AbsRefreshFooter(Context context) {
        super(context);
    }

    public abstract void onStartLoadMore();


    public abstract void onFinishLoadMore();
}
