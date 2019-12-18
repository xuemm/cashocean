package com.jike.cashocean.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.facebook.appevents.AppEventsLogger;
import com.jike.cashocean.R;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.ui.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Yey on 2018/1/21.
 */

public class SplashActivity extends BaseActivity {
    @Override
    public int getContentLayout() {
        return R.layout.activity_splash_cash;
    }

    @Override
    public void initInjector() {

    }

    @SuppressLint({"CheckResult", "MissingPermission"})
    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        AppEventsLogger.newLogger(this).logEvent(Key.START_APP);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ActivityUtils.startActivity(MainNewActivity.class);
            finish();
        }
    };

    @Override
    public void initData() {
        handler.sendEmptyMessageDelayed(0, 1500);
    }

    @Override
    public void onReloadClick(View v) {

    }


    @Override
    public boolean needUserLoadSir() {
        return false;
    }

}
