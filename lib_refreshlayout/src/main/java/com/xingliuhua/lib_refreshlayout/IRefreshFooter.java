package com.xingliuhua.lib_refreshlayout;

/**
 * Created by xingliuhua on 2016/7/6 0006.
 */
public interface IRefreshFooter extends IRefreshHeaderOrFooter {
    void onStartLoadMore();


    void onFinishLoadMore();
}
