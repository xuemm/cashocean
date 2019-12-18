package com.jike.cashocean.ui.find.compoment;

import com.jike.cashocean.ui.find.CardTestActivity;
import com.jike.cashocean.ui.find.PhoneTestActivity;
import com.jike.cashocean.ui.find.PhoneTestResultActivity;
import com.jike.cashocean.ui.find.contract.PhoneTestResultContract;
import com.jike.cashocean.ui.find.module.FindModule;
import com.jike.cashocean.ui.home.FindFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FindModule.class})
public interface FindComponent {
    void inject(FindFragment findFragment);

    void inject(CardTestActivity cardTestActivity);

    void inject(PhoneTestActivity phoneTestActivity);

    void inject(PhoneTestResultActivity phoneTestResultActivity);
}
