package com.xingliuhua.refreshlayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xingliuhua on 2016/7/8 0008.
 */
public class RefreshLayoutListViewAdapter extends BaseAdapter {
    private List<String> mStringList;

    private Context mContext;

    public RefreshLayoutListViewAdapter(List<String> stringList, Context context) {
        mStringList = stringList;
        mContext = context;
    }

    public List<String> getStringList() {
        return mStringList;
    }

    public void setStringList(List<String> stringList) {
        mStringList = stringList;
    }

    @Override
    public int getCount() {
        if (mStringList == null) {
            return 0;
        } else {
            return mStringList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(mStringList.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
    }
}
