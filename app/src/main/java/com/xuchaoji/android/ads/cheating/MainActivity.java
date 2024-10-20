package com.xuchaoji.android.ads.cheating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huawei.openalliance.ad.inter.HiAd;
import com.huawei.openalliance.ad.inter.IHiAd;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button startNative;
    private IHiAd hiad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdsSDK();
        startNative = findViewById(R.id.start_native_ad);
        startNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NativeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initAdsSDK() {
        long start = System.currentTimeMillis();
        Log.i(TAG, "initAdsSDK start");
        hiad = HiAd.getInstance(getApplicationContext());
        hiad.initLog(true, Log.INFO);
        hiad.enableUserInfo(true);
        Log.i(TAG, "initAdsSDK end cost: " + (System.currentTimeMillis() - start) + " ms.");
    }
}