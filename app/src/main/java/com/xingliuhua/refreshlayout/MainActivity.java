package com.xingliuhua.refreshlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_containListView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshListViewActivity.class));
            }
        });
        findViewById(R.id.btn_containLinearLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshLinearLayoutActivity.class));
            }
        });
        findViewById(R.id.btn_containListViewCustomHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshListViewCustomHeaderActivity.class));
            }
        });
     LottieAnimationView lottieAnimationView= findViewById(R.id.animation_view);
     lottieAnimationView.setProgress(0.4f);
    }
}
