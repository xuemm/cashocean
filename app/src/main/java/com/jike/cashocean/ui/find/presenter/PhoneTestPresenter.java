package com.jike.cashocean.ui.find.presenter;

import android.content.Context;

import com.blankj.utilcode.util.DeviceUtils;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.UserLevelBean;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.net.URL;
import com.jike.cashocean.ui.MainNewActivity;
import com.jike.cashocean.ui.base.BasePresenter;
import com.jike.cashocean.ui.find.PhoneTestActivity;
import com.jike.cashocean.ui.find.contract.PhoneTestContract;
import com.jike.cashocean.util.MapUrlTools;

import java.util.TreeMap;

import javax.inject.Inject;

/**
 * @author Ming
 * @Date on 2019/12/6
 * @Description
 */
public class PhoneTestPresenter extends BasePresenter<PhoneTestActivity> implements PhoneTestContract.Presenter {


    private RequestApi requestApi;
    private Context _context;

    @Inject
    public PhoneTestPresenter(RequestApi requestApi, Context context) {
        this.requestApi = requestApi;
        this._context = context;
    }

    @Override
    public void upLoadPhoneInfo(String imei, String gps) {
        TreeMap<String, String> paramsMap = new TreeMap<String, String>();
        paramsMap.put("gps", gps);
        paramsMap.put("advertising_id", MainNewActivity.advertising_id);
        paramsMap.put("mac", imei);
        paramsMap.put("android_edition", DeviceUtils.getSDKVersionCode() + "");
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap));
        requestApi.getUserLevel(paramsMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<UserLevelBean>() {
                    @Override
                    public void onSuccess(UserLevelBean userLevelBean) throws Exception {
                        mView.jumpInfo(userLevelBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.jumpInfo(null);
                    }
                });
    }


    /*@Override
    public void upLoadUserAppList() {
        String appList = PhoneReadUtils.getAppListNew();
        TreeMap<String, String> paramsMap = getParamesMap(appList);
        requestApi.uploadUserInfo(URL.UPLOAD_USER_APP_LIST, paramsMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<NormalBean>() {

                    @Override
                    public void onSuccess(NormalBean normalBean) throws Exception {
                        mView.uploadUserAppResult(normalBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.uploadUserAppResult(null);
                    }
                });
    }*/

   /* @Override
    public void upLoadUserContent() {
        String appList = PhoneReadUtils.getAllContactInfo();
        TreeMap<String, String> paramsMap = new TreeMap<>();
        paramsMap.put("contacts", appList);
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap));
        requestApi.uploadUserInfo(URL.USER_CONTEAC, paramsMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<NormalBean>() {

                    @Override
                    public void onSuccess(NormalBean normalBean) throws Exception {
                        mView.uploadUserContentResult(normalBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.uploadUserContentResult(null);
                    }
                });
    }*/
}
