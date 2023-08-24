package com.example.world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class floatwin extends AppCompatActivity {
    private Button btnShow;
    FloatWindow floatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatwin);
        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(getApplicationContext())) {
                // 启动Activity让用户授权
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,10);
            } else {
                // 执行6.0以上绘制代码
                initView();
            }
        } else {
            // 执行6.0以下绘制代码
            initView();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        // 权限判断
        if (Build.VERSION.SDK_INT >= 23) {
            if(Settings.canDrawOverlays(getApplicationContext())) {
                initView();
            }
        } else {
            //执行6.0以下绘制代码
            initView();
        }
    }
    private void initView() {
        setContentView(R.layout.activity_floatwin);
        floatWindow = new FloatWindow(getApplicationContext());

        btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != floatWindow) {
                    floatWindow.showFloatWindow();
                }
            }
        });

        Button btnrefresh = findViewById(R.id.btn_refresh);
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int random = (int) (Math.random() * 10);
                if (null != floatWindow) {
                    floatWindow.updateText(String.valueOf(random));
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != floatWindow) {
            floatWindow.hideFloatWindow();
        }
    }



}