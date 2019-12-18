package com.jike.cashocean.ui.bill.module;

import android.content.Context;

import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RetrofitInit;
import com.jike.cashocean.ui.home.api.ApiHome;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class BillModule {
    private final RequestApi apiHome;
    private Context _context;


    public BillModule(String tag, Context context) {
        apiHome = RetrofitInit.getRetrofit(tag).create(RequestApi.class);
        _context = context;
    }

    @Singleton
    @Provides
    RequestApi providerCacheApi() {
        return apiHome;
    }

    @Singleton
    @Provides
    Context providerContext() {
        return _context;
    }
}
