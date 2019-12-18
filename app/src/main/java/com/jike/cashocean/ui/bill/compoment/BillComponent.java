package com.jike.cashocean.ui.bill.compoment;

import com.jike.cashocean.ui.bill.AddBillActivity;
import com.jike.cashocean.ui.bill.AllBillActivity;
import com.jike.cashocean.ui.bill.BillInfoActivity;
import com.jike.cashocean.ui.bill.module.BillModule;
import com.jike.cashocean.ui.home.BookkeeperFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {BillModule.class})
public interface BillComponent {
    void inject(AddBillActivity addBillActivity);

    void inject(BookkeeperFragment bookkeeperFragment);

    void inject(AllBillActivity allBillActivity);
}
