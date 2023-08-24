package com.example.world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private NotificationManager manager;
    private Notification notification;

    private int health;//电池健康度的变量
    private int  max;//最大的电池容量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void gotoGitHUb(View view) {
        String url = "https://github.com/Justin-love-android";//个人主页的url
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);//通过Intent前往个人主页
    }




    public void gotodianchi(View view) {
        startActivity(new Intent(this, dianchi.class));
    }


    public void gotorunscore(View view) {
        startActivity(new Intent(this, floatwin.class));
    }
}