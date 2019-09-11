package com.xingliuhua.lib_refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public abstract class AbsRefreshHeder extends FrameLayout implements IRefreshHeaderOrFooter {
    public AbsRefreshHeder(Context context) {
        super(context);
    }


    public abstract void onStartRefreshing();
    public abstract void onFinishRefreshing();
}
