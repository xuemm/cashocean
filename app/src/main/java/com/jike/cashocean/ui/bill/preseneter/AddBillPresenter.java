package com.jike.cashocean.ui.bill.preseneter;

import android.content.Context;

import com.jike.cashocean.Content.Code;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.net.URL;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.base.BasePresenter;
import com.jike.cashocean.ui.bill.AddBillActivity;
import com.jike.cashocean.ui.bill.contract.BillContract;
import com.jike.cashocean.ui.home.api.ApiHome;
import com.jike.cashocean.util.MapUrlTools;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class AddBillPresenter extends BasePresenter<AddBillActivity> implements BillContract.Presenter {
    private RequestApi requestApi;
    private Context _context;

    @Inject
    public AddBillPresenter(RequestApi requestApi, Context _context) {
        this.requestApi = requestApi;
        this._context = _context;
    }

    private TreeMap<String, String> getParamesMap() {
        TreeMap<String, String> paramsMap = new TreeMap<>();
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap, false));
        return paramsMap;
    }

    @Override
    public void addBill(String appName, String repay_money, String repay_time) {

        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("app_name", appName);
        treeMap.put("repay_money", repay_money);
        treeMap.put("repay_time", repay_time);
        treeMap.put(Key.SIGN, MapUrlTools.getSign(treeMap, true));
        requestApi.postRequest(URL.ADD_BILL, treeMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<NormalBean>() {

                    @Override
                    public void onSuccess(NormalBean normalBean) throws Exception {

                        mView.addBillResult(normalBean);

                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.addBillResult(null);
                    }
                });
    }

    @Override
    public void getSystemApp() {
        requestApi.getSystemApp(getParamesMap())
                .compose(RxSchedulers.<SystemAppBean>applySchedulers())
                .compose(mView.<SystemAppBean>bindToLife())
                .subscribe(new BaseObserver<SystemAppBean>() {
                    @Override
                    public void onSuccess(SystemAppBean systemAppBean) throws Exception {
                        mView.getSystemApplist(systemAppBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.getSystemApplist(null);
                    }
                });
    }


}
