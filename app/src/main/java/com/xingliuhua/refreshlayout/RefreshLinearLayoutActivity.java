package com.xingliuhua.refreshlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xingliuhua.lib_refreshlayout.RefreshLayout;

/**
 * Created by xingliuhua on 2016/7/8 0008.
 */
public class RefreshLinearLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayout);
        init();
    }


    private void init() {
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {

            }
        });
        refreshLayout.setNeedLoadMore(false);
        refreshLayout.setRefreshing(true);
    }
}
