package com.jike.cashocean.ui.home.presenter;

import android.content.Context;

import com.jike.cashocean.model.FindHomeBean;
import com.jike.cashocean.model.FindInfoBean;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.net.URL;
import com.jike.cashocean.ui.base.BasePresenter;
import com.jike.cashocean.ui.find.contract.FindHomeContract;
import com.jike.cashocean.ui.home.FindFragment;
import com.jike.cashocean.util.MapUrlTools;

import java.util.TreeMap;

import javax.inject.Inject;

/**
 * Created by Yey on 2018/5/5.
 */

public class FindPresenter extends BasePresenter<FindFragment> implements FindHomeContract.Presenter {
    private RequestApi requestApi;
    private Context _context;

    @Inject
    public FindPresenter(RequestApi requestApi, Context context) {
        this.requestApi = requestApi;
        this._context = context;
    }

    @Override
    public void getFindHomeList(int page, int perpage) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("page", page);
        treeMap.put("perpage", perpage);
        treeMap.put(Key.SIGN, MapUrlTools.getSignObject(treeMap, true));
        requestApi.getFindList(URL.FIND_INDEX, treeMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<FindHomeBean>() {
                    @Override
                    public void onSuccess(FindHomeBean findHomeBean) throws Exception {
                        mView.showFindHome(findHomeBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.showFindHome(null);
                    }
                });

    }

    @Override
    public void getInfo(int id) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("id", id);
        treeMap.put(Key.SIGN, MapUrlTools.getSignObject(treeMap, true));
        requestApi.getFindInfo(treeMap)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<FindInfoBean>() {
                    @Override
                    public void onSuccess(FindInfoBean findInfoBean) throws Exception {
                        mView.jumpInfo(findInfoBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.jumpInfo(null);
                    }
                });
    }
}

