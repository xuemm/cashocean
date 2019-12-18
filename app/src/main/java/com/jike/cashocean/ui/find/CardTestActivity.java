package com.jike.cashocean.ui.find;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jike.cashocean.R;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.ui.authentication.PhotoActivity;
import com.jike.cashocean.ui.authentication.PhotoFaceActivity;
import com.jike.cashocean.ui.base.BaseActivity;
import com.jike.cashocean.ui.find.compoment.DaggerFindComponent;
import com.jike.cashocean.ui.find.contract.CardTestContract;
import com.jike.cashocean.ui.find.module.FindModule;
import com.jike.cashocean.ui.find.presenter.CardTestPresenter;
import com.jike.cashocean.util.BitmapUtil;
import com.jike.cashocean.util.DialogTools;
import com.jike.cashocean.util.MapUrlTools;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class CardTestActivity extends BaseActivity<CardTestPresenter> implements CardTestContract.View {

    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.im_identity_id_car_photo)
    ImageView imIdentityIdCarPhoto;
    @BindView(R.id.im_face_photo)
    ImageView imFacePhoto;
    @BindView(R.id.tv_card_confirm)
    TextView tvCardConfirm;

    @Override
    public int getContentLayout() {
        return R.layout.activity_card_test;
    }

    @Override
    public void initInjector() {
        DaggerFindComponent.builder().findModule(new FindModule("证件测评界面", this)).build().inject(this);
    }

    private static final int CODE_FACE = 147;
    private static final int CODE_ID_PHOTO = 258;

    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        tvTitle.setText(getString(R.string.test_card_title));
        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RxView.clicks(imIdentityIdCarPhoto)
                .compose(this.bindToLife())
                .compose(new RxPermissions(this).ensure(Manifest.permission.CAMERA))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        startActivityForResult(new Intent(CardTestActivity.this,
                                PhotoActivity.class), CODE_ID_PHOTO);
                    } else {
                        DialogTools.dialogPermission(this,
                                getString(R.string.open_camera_permission));
                    }
                });
        RxView.clicks(imFacePhoto)
                .compose(this.bindToLife())
                .compose(new RxPermissions(this).ensure(Manifest.permission.CAMERA))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        startActivityForResult(new Intent(CardTestActivity.this,
                                PhotoFaceActivity.class), CODE_FACE);
                    } else {
                        DialogTools.dialogPermission(this,
                                getString(R.string.open_camera_permission));
                    }
                });

        RxView.clicks(tvCardConfirm)
                .compose(this.bindToLife())
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (judgeIdentityInfo(true)) {
                        showLoadingDialog();
                        Map<String, String> parameMap = getParameMap();

                        mPresenter.saveInfo(parameMap);
                    }
                });

    }

    public Map<String, String> getParameMap() {
        TreeMap<String, String> paramsMap = new TreeMap<>();
        paramsMap.put("idnumber_img", _idName);
        paramsMap.put("face_img", _faceName);
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap));
        return paramsMap;
    }

    //判断信息是否填写完整
    public boolean judgeIdentityInfo(boolean showHint) {

        //判断证件照片
        if (TextUtils.isEmpty(_idName)) {
            if (showHint) {
                ToastUtils.showLong(getString(R.string.please_id_photo));
            }
            return false;
        }
        //判断人脸照片
        if (TextUtils.isEmpty(_faceName)) {
            if (showHint) {
                ToastUtils.showLong(getString(R.string.please_face_photo));
            }
            return false;
        }
        return true;
    }

    //初始化图片路径
    private void initPicturePath() {
        facePicturePath =
                getCacheDir().getAbsolutePath() + File.separator + Key.PATH_FACE_PHOTO_PICTURE;
        facePicturePathShow =
                getCacheDir().getAbsolutePath() + File.separator + Key.PATH_FACE_PHOTO_PICTURE_SHOW;
        idCardPath = getCacheDir().getAbsolutePath() + File.separator + Key.PATH_ID_CARD_PICTURE;
        idCardPathShow =
                getCacheDir().getAbsolutePath() + File.separator + Key.PATH_ID_CARD_PICTURE_SHOW;
    }

    private String getPhotoName() {
        String nameStr =
                String.valueOf(System.currentTimeMillis()) + SPUtils.getInstance().getString(Key.TOKEN);
        return EncryptUtils.encryptMD5ToString(nameStr) + ".png";
    }

    private String facePicturePath;
    private String facePicturePathShow;
    private String idCardPath;
    private String idCardPathShow;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理自拍照片
        if (requestCode == CODE_FACE && resultCode == RESULT_OK) {
            String path = data.getStringExtra("path");
            FileUtils.copyFile(path, facePicturePath);
            String faceName = getPhotoName();
            showLoadingDialog();
            mPresenter.pushPhoto(faceName, path, false);
        }
        //证件照
        if (requestCode == CODE_ID_PHOTO && resultCode == RESULT_OK) {
            String path = data.getStringExtra("path");
            FileUtils.copyFile(path, idCardPath);
            String idName = getPhotoName();
            showLoadingDialog();
            mPresenter.pushPhoto(idName, path, true);
        }
    }

    @Override
    public void initData() {
        initPicturePath();
    }

    @Override
    public void onReloadClick(View v) {

    }

    @Override
    public boolean needUserLoadSir() {
        return false;
    }


    @Override
    public void loadSaveInfoSuccess(NormalBean normalBean) {
        hideLoadingDialog();
        if (normalBean != null) {
            if (normalBean.getRet() == 200) {
                if (normalBean.getData().getCode() == 100) {
                    //跳转证件测评界面
                    ActivityUtils.startActivity(CardTestResultActivity.class);
                    finish();
                } else {
                    ActivityUtils.startActivity(CardTestFailActivity.class);
                }
            } else {
                ToastUtils.showLong(normalBean.getMsg());
            }
        }
    }

    private String _idName;

    @Override
    public void loadScuccessIDPhoto(String idName) {
        hideLoadingDialog();
        if (idName != null) {
            _idName = idName;
            Bitmap idPhotoBitmapShow = BitmapUtil.decodeSampledBitmapFromFile(idCardPath,
                    imIdentityIdCarPhoto.getWidth(), imIdentityIdCarPhoto.getHeight());
            ImageUtils.save(idPhotoBitmapShow, idCardPathShow, Bitmap.CompressFormat.JPEG);
            imIdentityIdCarPhoto.setImageBitmap(idPhotoBitmapShow);
        }
    }

    private String _faceName;

    @Override
    public void loadSuccessFacePhoto(String faceName) {
        hideLoadingDialog();
        if (faceName != null) {
            _faceName = faceName;
            Bitmap bitmapFaceShow = BitmapUtil.decodeSampledBitmapFromFile(facePicturePath,
                    imFacePhoto.getWidth(), imFacePhoto.getHeight());
            ImageUtils.save(bitmapFaceShow, facePicturePathShow, Bitmap.CompressFormat.JPEG);
            imFacePhoto.setImageBitmap(bitmapFaceShow);
        }
    }
}
