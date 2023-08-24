package com.example.world;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class dianchi extends AppCompatActivity {

    private Button btnBattery;
    private LinearLayout panelBattery;
    private TextView txtBattery;
    private ProgressBar progressBar;
    private ProgressBar pro_rl;
    private ProgressBar pro_hea;
    private TextView txt_ril;//显示电池容量的TextView
    private TextView txt_hea;//显示电池健康的TextView
    private double dianchirl;//电池容量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianchi);
        float batteryTemperature = getBatteryTemperature();

        btnBattery = findViewById(R.id.btnBattery);
        panelBattery = findViewById(R.id.panelBattery);
        txtBattery = findViewById(R.id.txtBattery);
        progressBar = findViewById(R.id.progressBar);
        txt_ril = findViewById(R.id.txt_rl);
        pro_rl = findViewById(R.id.pro_irl);
        txt_hea = findViewById(R.id.txt_he);
        pro_hea = findViewById(R.id.pro_hea);
        LinearLayout lin_hea = findViewById(R.id.lin_health);

        final float initialY = panelBattery.getTranslationY();
        final float initialX = panelBattery.getTranslationX();




        btnBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBattery.setVisibility(View.GONE);
                lin_hea.setVisibility(View.GONE);

                pro_rl.setVisibility(View.VISIBLE);
                pro_hea.setVisibility(View.VISIBLE);


                panelBattery.setVisibility(View.VISIBLE);
                panelBattery.setAlpha(0.0f); // 初始透明度为0

                // 计算面板动画的位置
                int panelWidth = panelBattery.getWidth();
                int panelHeight = panelBattery.getHeight();
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;

                int translationX = (screenWidth - panelWidth) / 2;
                int translationY = (screenHeight - panelHeight) / 2;

                // 创建面板动画
                ObjectAnimator panelAnimator = ObjectAnimator.ofFloat(panelBattery, "translationX", -translationX, 0);
                panelAnimator.setDuration(500);
                panelAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

                // 透明度动画
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(panelBattery, "alpha", 0.0f, 1.0f);
                alphaAnimator.setDuration(500);

                // 同时执行面板动画和透明度动画
                panelAnimator.start();
                alphaAnimator.start();

                txtBattery.setText("正在获取电池信息");
                progressBar.setVisibility(View.VISIBLE);

                // 随机生成1-3之间的数字，作为面板显示时间
                int randomTime = new Random().nextInt(3) + 1;

                // 延时隐藏面板
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 创建面板隐藏动画
                        ObjectAnimator hideAnimator = ObjectAnimator.ofFloat(panelBattery, "translationY", 0, translationY);
                        hideAnimator.setDuration(500);

                        // 同时执行面板隐藏动画和透明度动画
                        hideAnimator.start();
                        alphaAnimator.reverse();

                        hideAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                lin_hea.setVisibility(View.VISIBLE);
                                panelBattery.setVisibility(View.GONE);
                                pro_hea.setVisibility(View.GONE);
                                pro_rl.setVisibility(View.GONE);
                                panelBattery.setTranslationY(initialY);
                                panelBattery.setTranslationX(initialX);

                                dianchirl = getBatteryCapacity(getApplication());

                                String str_rl = String.valueOf(dianchirl);



                                txt_ril.setText("电池容量："+ str_rl +"mAh");
                                String temperatureText = String.format("%.1f℃", batteryTemperature);
                                txt_hea.setText("电池温度："+ temperatureText);



                                btnBattery.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }, randomTime * 1000);
            }
        });
    }
    public double getBatteryCapacity(Context context) {
        double batteryCapacity = 0;

        try {
            Object powerProfile = Class.forName("com.android.internal.os.PowerProfile").getConstructor(Context.class).newInstance(context);
            Method getBatteryCapacityMethod = powerProfile.getClass().getMethod("getBatteryCapacity");
            batteryCapacity = (double) getBatteryCapacityMethod.invoke(powerProfile);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return batteryCapacity;
    }
    private float getBatteryTemperature() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = registerReceiver(null, intentFilter);

        if (batteryStatusIntent != null) {
            int temperature = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            return temperature / 10.0f;
        }

        return 0;
    }


}
