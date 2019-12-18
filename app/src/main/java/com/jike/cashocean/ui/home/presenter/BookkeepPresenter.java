package com.jike.cashocean.ui.home.presenter;

import android.content.Context;

import com.jike.cashocean.model.BillAppBean;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.model.UserBillInfoBean;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.net.URL;
import com.jike.cashocean.ui.base.BasePresenter;
import com.jike.cashocean.ui.home.BookkeeperFragment;
import com.jike.cashocean.ui.bill.contract.BillHomeContract;
import com.jike.cashocean.util.MapUrlTools;

import java.util.TreeMap;

import javax.inject.Inject;

/**
 * Created by Yey on 2018/5/5.
 */

public class BookkeepPresenter extends BasePresenter<BookkeeperFragment> implements BillHomeContract.Presenter {
    private RequestApi requestApi;
    private Context _context;

    @Inject
    public BookkeepPresenter(RequestApi requestApi, Context context) {
        this.requestApi = requestApi;
        this._context = context;
    }

    @Override
    public void getBillHomeList(int page, int perpage) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("page", page);
        treeMap.put("perpage", perpage);
        treeMap.put("status", "1");
        treeMap.put(Key.SIGN, MapUrlTools.getSignObject(treeMap, true));
        requestApi.getAppList(URL.GET_USER_BILL_INFO, treeMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<BillAppBean>() {
                    @Override
                    public void onSuccess(BillAppBean billAppBean) throws Exception {
                        mView.showBillHome(billAppBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.showBillHome(null);
                    }
                });
    }

    @Override
    public void getUserBill() {
        requestApi.getUserBill(getParamesMap(true))
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<UserBillInfoBean>() {
                    @Override
                    public void onSuccess(UserBillInfoBean userBillInfoBean) throws Exception {
                        mView.getUserBillResult(userBillInfoBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.getUserBillResult(null);
                    }
                });
    }

    private TreeMap<String, String> getParamesMap(boolean isNeetToken) {
        TreeMap<String, String> paramsMap = new TreeMap<String, String>();
        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap, isNeetToken));
        return paramsMap;
    }

    @Override
    public void getSystemApp() {
        requestApi.getSystemApp(getParamesMap(false))
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

    @Override
    public void changeBillStatus(int id, String status) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("id", id + "");
        treeMap.put("status", status);
        treeMap.put(Key.SIGN, MapUrlTools.getSign(treeMap, true));
        requestApi.postRequest(URL.UPDATE_BILL_STATUS, treeMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<NormalBean>() {
                    @Override
                    public void onSuccess(NormalBean normalBean) throws Exception {
                        mView.changeBillStatusResult(normalBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.changeBillStatusResult(null);
                    }
                });
    }

}
