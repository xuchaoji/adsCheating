package com.xuchaoji.android.ads.cheating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.controls.DeviceTypes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    private float adViewWidth = 984;

    private float adViewHeight = 649;

    private float updateW = adViewWidth;

    private float updateH = adViewHeight;
    private SeekBar widthSeekBar;
    private TextView widthTextView;

    private SeekBar heightSeekBar;
    private TextView heightTextView;

    private SeekBar alphaSeekBar;

    private TextView alphaTextView;

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
        widthSeekBar = findViewById(R.id.view_width_seekbar);
        widthTextView = findViewById(R.id.view_width_tv);
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateW = adViewWidth * progress / 100;
                widthTextView.setText("控件宽: " + updateW + " px.");
                updateAdViewSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        heightSeekBar = findViewById(R.id.view_height_seekbar);
        heightTextView = findViewById(R.id.view_height_tv);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateH = adViewHeight * progress / 100;
                heightTextView.setText("控件高: " + updateH + " px.");
                updateAdViewSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        alphaSeekBar = findViewById(R.id.view_alpha_seekbar);
        alphaTextView = findViewById(R.id.view_alpha_tv);
        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float alpha = progress / 100f;
                alphaTextView.setText("透明度" + alpha);
                if (nativeView != null) {
                    nativeView.setAlpha(alpha);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void updateAdViewSize() {
        if (nativeView == null) {
            return;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) updateW, (int) updateH);
        params.addRule(RelativeLayout.BELOW, R.id.cheatting_param_container);
        nativeView.setLayoutParams(params);
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
        nativeView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i(TAG, "onGlobalLayout, w: " + nativeView.getWidth());
                Log.i(TAG, "onGlobalLayout, h: " + nativeView.getHeight());
            }
        });
    }
}