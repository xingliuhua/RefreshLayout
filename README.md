# RefreshLayout
This one pull to refresh library, support for load more, it can contain a LinearLayout or ListView and other layout
##Screenshot
![](https://github.com/xingliuhua/RefreshLayout/blob/master/listview.gif)![](https://github.com/xingliuhua/RefreshLayout/blob/master/listview2.gif)
##Getting Started

###Add the library as dependency

Add the library as dependency to your `build.gradle` file.

```java
dependencies {
	//other dependencies...
	compile 'com.xingliuhua:lib_refreshlayout:1.0.4'
}
```
###Include the View into your Layout

Add the View to your existing layout file.
```xml
<com.xingliuhua.lib_refreshlayout.RefreshLayout
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:footerLoadMoreingText="加载中..."
        app:footerPullText="上拉加载更多"
        app:headerAnimDrawbleList="@drawable/sun_refreshing">

        <ListView
            android:id="@+id/listView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </com.xingliuhua.lib_refreshlayout.RefreshLayout>
```
###Customize the headerAnimList
```xml
<animation-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:drawable="@drawable/sun_1"
        android:duration="200"></item>
    <item
        android:drawable="@drawable/sun_2"
        android:duration="200"></item>
    <item
        android:drawable="@drawable/sun_3"
        android:duration="200"></item>
</animation-list>
```
###use in code
```java
 refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                
            }

            @Override
            public void onLoadmore() {

            }
        });
 refreshLayout.setRefreshing(true); //start or stop refreshing
 refreshLayout.setLoadMoreing(false); //just start or stop loadMore
 refreshLayout.setLoadMoreing(false,"已显示所有数据"); // start loadmore,or stop loadmore and hava a textview message(ex:hava no data)
```

