package com.xuchaoji.android.ads.cheating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.controls.DeviceTypes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huawei.openalliance.ad.inter.INativeAdLoader;
import com.huawei.openalliance.ad.inter.NativeAdLoader;
import com.huawei.openalliance.ad.inter.data.INativeAd;
import com.huawei.openalliance.ad.inter.data.ImageInfo;
import com.huawei.openalliance.ad.inter.listeners.NativeAdListener;
import com.huawei.openalliance.ad.views.NativeVideoView;
import com.huawei.openalliance.ad.views.PPSNativeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NativeActivity extends AppCompatActivity {
    private static final String TAG = "NativeActivity";
    public static final int DEVICE_PHONE = 4;
    public static final String TEST_AD_ID = "testy63txaom86";

    private INativeAdLoader adLoader;

    private PPSNativeView nativeView;

    private TextView adTitle;

    private ImageView adImg;

    private NativeVideoView adVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        initAdViews();
        initCheatingParamViews();

        loadAd();
    }

    private void loadAd() {
        adLoader = new NativeAdLoader(this, new String[]{TEST_AD_ID});
        adLoader.enableDirectReturnVideoAd(true);
        // 支持模板渲染
        adLoader.setSupportTptAd(true);
        adLoader.setListener(new NativeAdListener() {
            @Override
            public void onAdsLoaded(Map<String, List<INativeAd>> map) {
                Log.i(TAG, "onAdsLoaded.");
                showAd(map.get(TEST_AD_ID).get(0));
            }

            @Override
            public void onAdFailed(int i) {
                Log.i(TAG, "onAdFailed, code: " + i);
            }
        });
        adLoader.loadAds(DEVICE_PHONE, false);
    }

    private void initCheatingParamViews() {
    }

    private void initAdViews() {
        nativeView = findViewById(R.id.native_view);
        adTitle = findViewById(R.id.pps_title_tv);
        adVideoView = findViewById(R.id.pps_video);
    }

    private void showAd(INativeAd nativeAd) {
        if (nativeAd == null) {
            Log.w(TAG, "showAd, ad is null.");
            return;
        }
        List<View> clickableViews = new ArrayList<>();

        clickableViews.add(adTitle);
        clickableViews.add(adVideoView);
        adTitle.setText(nativeAd.getTitle());

        nativeView.register(nativeAd, clickableViews);
        nativeView.register(nativeAd, adVideoView);
    }
}