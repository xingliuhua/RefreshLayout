package com.xingliuhua.refreshlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.xingliuhua.lib_refreshlayout.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingliuhua on 2016/7/8 0008.
 */
public class RefreshListViewActivity extends AppCompatActivity {
    private List<String> mStringList = new ArrayList<>();
    private RefreshLayoutListViewAdapter mRefreshLayoutListViewAdapter;
    private int itemIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        init();
    }


    private void init() {
        for (int i = 0; i < 10; i++) {
            mStringList.add("this is item" + itemIndex++);
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        mRefreshLayoutListViewAdapter = new RefreshLayoutListViewAdapter(mStringList, this);
        listView.setAdapter(mRefreshLayoutListViewAdapter);
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
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (itemIndex < 40) {
                            for (int i=0; i < 10; i++) {
                                mStringList.add("this is item" + itemIndex++);
                            }
                            mRefreshLayoutListViewAdapter.setStringList(mStringList);
                            mRefreshLayoutListViewAdapter.notifyDataSetChanged();
                        } else {
                            refreshLayout.setNeedLoadMore(false);
                        }
                        refreshLayout.setLoadMoreing(false);
                    }
                }, 2000);
            }
        });
    }
}
