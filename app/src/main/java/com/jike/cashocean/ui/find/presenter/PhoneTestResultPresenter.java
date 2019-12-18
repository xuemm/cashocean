package com.jike.cashocean.ui.find.presenter;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jike.cashocean.model.ClickAppListEntity;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.ui.MainNewActivity;
import com.jike.cashocean.ui.base.BasePresenter;
import com.jike.cashocean.ui.find.PhoneTestResultActivity;
import com.jike.cashocean.ui.find.contract.PhoneTestResultContract;
import com.jike.cashocean.util.MapUrlTools;

import java.util.TreeMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;

/**
 * @author Ming
 * @Date on 2019/12/11
 * @Description
 */
public class PhoneTestResultPresenter extends BasePresenter<PhoneTestResultActivity> implements PhoneTestResultContract.Presenter {

    RequestApi requestApi;
    Context context;

    @Inject
    public PhoneTestResultPresenter(RequestApi requestApi, Context context) {
        this.requestApi = requestApi;
        this.context = context;
    }

    @Override
    public void getCommendApp() {
        TreeMap<String, Object> paramsMap = new TreeMap();
        paramsMap.put(Key.SIGN, MapUrlTools.getSignObject(paramsMap, true));
        requestApi.getUserCommendApp(paramsMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<HomeListData>() {
                    @Override
                    public void onSuccess(HomeListData homeListData) throws Exception {
                        mView.loadCommendApp(homeListData);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.loadCommendApp(null);
                    }
                });
    }

    @Override
    public void clickItem(String id) {
        TreeMap<String, String> paramsMap = new TreeMap<>();
        paramsMap.put(Key.VERSION, String.valueOf(AppUtils.getAppVersionCode()));
        paramsMap.put(Key.TOKEN, SPUtils.getInstance().getString(Key.TOKEN));
        paramsMap.put(Key.ANDROID_ID, DeviceUtils.getAndroidID());
        paramsMap.put(Key.ADVERTISING_ID, MainNewActivity.advertising_id);
        paramsMap.put("appid", id);
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap));
        requestApi.clickItemApp(paramsMap)
                .compose(RxSchedulers.<ClickAppListEntity>applySchedulers())
                .compose(mView.<ClickAppListEntity>bindToLife())
                .subscribe(new BaseObserver<ClickAppListEntity>() {
                    @Override
                    public void onSuccess(ClickAppListEntity clickAppListEntity) throws Exception {
                        mView.loadClick(clickAppListEntity);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.loadClick(null);
                    }
                });

    }

    public void requestAf(String click_url, String package_name) {
        requestApi.RequestAf(click_url)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) throws Exception {
                        mView.openGoogle(package_name);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.openGoogle(package_name);
                    }
                });
    }
}
