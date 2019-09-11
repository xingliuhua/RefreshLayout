# RefreshLayout
This one pull to refresh library, support for load more, it can contain a LinearLayout or ListView and other layout.
you can custom heder or footer.
## Screenshot
![](https://github.com/xingliuhua/RefreshLayout/blob/master/demo.gif)
## Getting Started

### Add the library as dependency

Add the library as dependency to your `build.gradle` file.

```java
dependencies {
	//other dependencies...
	compile 'com.xingliuhua:lib_refreshlayout:2.0'
}
```
### Include the View into your Layout

Add the View to your existing layout file:

```xml
 <com.xingliuhua.lib_refreshlayout.RefreshLayout
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ListView
            android:id="@+id/listView"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </com.xingliuhua.lib_refreshlayout.RefreshLayout>
```

setOnRefreshListener in java:

```java
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
                        // refreshLayout.setNeedLoadMore(false);
                        refreshLayout.setLoadMoreing(false);
                    }
                }, 2000);
            }
        });
```

### Customize the header

extends AbsRefreshHeder:

```java
public class MyRefreshLayoutHeader extends AbsRefreshHeder {
    @Override
    public void onStartRefreshing() {

    }

    @Override
    public void onPull(float dy) {

    }

    @Override
    public void onFinishRefreshing() {

    }

}
```

set header height:

```xml
<com.xingliuhua.lib_refreshlayout.RefreshLayout
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:headerHeight="80dp">

        <ListView
            android:id="@+id/listView"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

</com.xingliuhua.lib_refreshlayout.RefreshLayout>
```

set header use in code:

```java
  refreshLayout.setRefreshHeder(new MyRefreshLayoutHeader(this));
```

### Customize the footer

extends AbsRefreshFooter:

```java
public class SimpleRefreshLayoutFooter extends AbsRefreshFooter {
    @Override
    public void onStartRefreshing() {

    }

    @Override
    public void onPull(float dy) {

    }

    @Override
    public void onFinishRefreshing() {

    }
}
```

set footer in java:

```java
refreshLayout.setRefreshFooter(new MyRefreshLayoutFooter(this));
```
