package com.jike.cashocean.ui.find.module;

import android.content.Context;

import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RetrofitInit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class FindModule {
    private final RequestApi apiHome;
    private Context _context;


    public FindModule(String tag, Context context) {
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
